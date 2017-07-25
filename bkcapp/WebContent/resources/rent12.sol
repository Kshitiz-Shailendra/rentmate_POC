pragma solidity ^0.4.8;
import "./strings.sol";
contract Property{
    
    using strings for *;
    
    string rent;
	string securityDeposit;
	uint postedTimestamp;
	string PropertyRegNo;
	string PropertyAddr;
	address ownerAddr; // owner's  wallet address
	string ownerName;
	string houseSize;
	string aboutProperty;
	
	function Property(string _rent,string _securityDeposit,string _PropertyRegNo,
	    string _PropertyAddr,string _ownerName,string _houseSize,string _aboutProperty){
	        
	        rent = _rent;
	        securityDeposit = _securityDeposit;
	        PropertyRegNo = _PropertyRegNo;
	        PropertyAddr = _PropertyAddr;
	        ownerName = _ownerName;
	        houseSize = _houseSize;
	        aboutProperty = _aboutProperty;
	        ownerAddr = tx.origin;
	        postedTimestamp = now;
	  }
	  
    function getPropertydetails() constant returns(string cString, address _ownerAddr,uint postedTimeStamp){
        
        var parts = new strings.slice[](7);
        parts[0] = rent.toSlice();
        parts[1] = securityDeposit.toSlice();
        parts[2] = PropertyRegNo.toSlice();
        parts[3] = PropertyAddr.toSlice();
        parts[4] = ownerName.toSlice();
        parts[5] = houseSize.toSlice();
        parts[6] = aboutProperty.toSlice();
        
        cString = ",".toSlice().join(parts);
        
        return (cString,ownerAddr,postedTimestamp);
    }
    
    /*function getOwnerInfo() constant returns(address _ownerAddr, string _ownerName){
        _ownerAddr = ownerAddr;
        _ownerName = ownerName;
        return (_ownerAddr,_ownerName);
    }*/
}


contract RentalAgreement{
    
    using strings for *;
    
    /*event agreementConfirmed();

    event paidRent();

    event contractTerminated();
    
    struct PaidRent {
    uint id;  The paid rent id
    uint value;  The amount of rent that is paid
    }*/

   //PaidRent[] paidrents;

    uint createdTimestamp;
    uint public rentPaidCount;
    string public rent;
    /* Combination of zip code and house number*/
    string PropertyRegNo;
    address landlord;
    address tenant;
    enum State {Created, Started, Terminated}
    State state;
    
    
    function RentalAgreement(string _rent, string _PropertyRegNo,address _tenant) {
        rent = _rent;
        PropertyRegNo = _PropertyRegNo;
        landlord = tx.origin;
        tenant = _tenant;
        createdTimestamp = block.timestamp;
    }
   /* modifier required(bool _condition) {
        require(_condition);
        _;
    }*/
    modifier onlyLandlord() {
        require(msg.sender == landlord);
        _;
    }
    modifier onlyTenant() {
        require (msg.sender == tenant);
        _;
    }
    modifier inState(State _state) {
        require(state == _state);
        _;
    }
    
   /* function confirmAgreement()  inState(State.Created)  onlyTenant{
       // agreementConfirmed();
        tenant = msg.sender;
        state = State.Started;
    }*/

    function payRent(uint rent) payable onlyTenant  inState(State.Started){
       // paidRent();
        landlord.transfer(rent);
        rentPaidCount++;
        /*paidrents.push(PaidRent({
        id : paidrents.length + 1,
        value : msg.value
        }));*/
    }
    /* Terminate the contract so the tenant can’t pay rent anymore,
    and the contract is terminated */
    function terminateContract()  onlyLandlord{
       // contractTerminated();
        landlord.transfer(this.balance);
        /* If there is any value on the
               contract send it to the landlord*/
        state = State.Terminated;
    }
    
   function getAgreementDetails() constant returns(string record,uint _createdTimestamp,address _tenant,address _landlord){
       var parts = new strings.slice[](2);
        parts[0] = rent.toSlice();
        parts[1] = PropertyRegNo.toSlice();
        
        record = ",".toSlice().join(parts);
        
        _createdTimestamp = createdTimestamp;
        _tenant = tenant;
        _landlord = landlord;
        return(record,createdTimestamp,tenant,landlord);
   }
}


contract PropertyRegistry{
    using strings for *;
    
   // event saveSuccessfull(bytes32 indexed addr);
    //event RAcreated(bytes32 indexed addr);
    //save property details
    //0 = NotRented,1 = PartialRented ,2 = Rented
    enum StateRented {NotRented, PartialRented, Rented}
    struct PropertyState{
        string PropertyRegNo;
        string details;//(rent,username,userAddress,PropertyRegNo,housesize,aboutProperty)
        StateRented rented;
    }
   
    PropertyState[] properties;
    //sha3(propertyregno) => propertycontract address
    mapping(bytes32=>address) houses;
    //[owner][propertyno] => agreement address
    mapping(address=>mapping(bytes32=>address)) agreements;
    //tenantaddress => rentagreement address
    mapping(address=>address) tenantToAgreement;
  
  	modifier propExists(string PropertyRegNo){
		require(houses[sha3(PropertyRegNo)] == 0);
		_;
	}

	/* all functions from here */
	function insertRecord(string _rent,string _securityDeposit,string _PropertyRegNo,
	string _PropertyAddr,string _ownerName,string _houseSize,string _aboutProperty) 
							propExists(_PropertyRegNo){


		bytes32 idx = sha3(_PropertyRegNo);
		Property propobj = new Property(_rent, _securityDeposit, _PropertyRegNo,
	     _PropertyAddr, _ownerName, _houseSize, _aboutProperty);
        
        houses[idx] = propobj;
       /* var  propVar = joinStrings(_rent, _PropertyRegNo, _PropertyAddr, _ownerName, _houseSize);
       
        properties.push(PropertyState(_PropertyRegNo,propVar,false));*/
		//saveSuccessfull(idx);
        //return true;
	}
	function insertRecordDetailsArray(string _PropertyRegNo,string record){
	    properties.push(PropertyState(_PropertyRegNo,record,StateRented.NotRented));
	}
	//have to be called while creating or deleting rental agreements
	function updateRecordDetailsArray(string _PropertyRegNo,StateRented state){
	    var len = properties.length;
        for(uint i=0;i<len;i++){
           // propRegno = properties[i].PropertyRegNo;
            if(properties[i].PropertyRegNo.toSlice().compare(_PropertyRegNo.toSlice()) == 0){
                properties[i].rented = state;
                break;
            }
        }
	}
	//these could be used in future string _securityDeposit,address _owner
	function createAgreement(string _PropertyRegNo,string _rent,
            address _tenant)
            {
           
        RentalAgreement RAobj = new RentalAgreement( _rent,  _PropertyRegNo, _tenant);
        bytes32 idx = sha3(_PropertyRegNo);
        agreements[msg.sender][idx] = RAobj;
        tenantToAgreement[_tenant] = RAobj;
         updateRecordDetailsArray( _PropertyRegNo,StateRented.Rented);
        //RAcreated(idx);
        /*var len = properties.length;
        for(uint i=0;i<len;i++){
           // propRegno = properties[i].PropertyRegNo;
            if(properties[i].PropertyRegNo.toSlice().compare(_PropertyRegNo.toSlice()) == 0){
                properties[i].rented = true;
                break;
            }
        }*/
            
            
    }
    
    /*constant return functions here*/
    //state can be used to find rented or non-rented properties
	function getHouses(StateRented state) constant returns (string hArray){
        
        
        var len = properties.length;
        var parts = new strings.slice[]((len));
        uint idx = 0;
        for(uint i=0;i<len;i++){
            if(properties[i].rented == state){
                parts[idx] = properties[i].details.toSlice();
                idx++;
            }
            
        }
        hArray = ";".toSlice().join(parts);
        return (hArray);
    }
    function getPropertyAddr(string _PropertyRegNo) constant returns(address propAddr){
        propAddr = houses[sha3(_PropertyRegNo)];
        return (propAddr);
    }
    function getRentAgreementAddr(string _PropertyRegNo,address landlord) constant returns(address rentAgreementaddr){
        rentAgreementaddr = agreements[landlord][sha3(_PropertyRegNo)];
        return (rentAgreementaddr);
    }
    function getRentAgreementAddrFromTenantAddr(address tenant) constant returns(address rentAgreementaddr){
        rentAgreementaddr = tenantToAgreement[tenant];
        return (rentAgreementaddr);
    }
    /*internal functions here*/
   /* function joinStrings(string _rent,string _PropertyRegNo,string _PropertyAddr,string _ownerName,string _houseSize )
                constant internal returns (string cString){
        var parts = new strings.slice[](5);
        parts[0] = _rent.toSlice();
        parts[1] = _PropertyRegNo.toSlice();
        parts[2] = _PropertyAddr.toSlice();
        parts[3] = _ownerName.toSlice();
        parts[4] = _houseSize.toSlice();
        
        cString = ",".toSlice().join(parts);
        return cString;
        
    }*/
    
    
}
