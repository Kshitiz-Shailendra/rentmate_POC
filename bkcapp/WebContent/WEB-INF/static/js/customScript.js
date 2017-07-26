$(function() {
	$('#side-menu').metisMenu();
});

// Loads the correct sidebar on window load,
// collapses the sidebar on window resize.
// Sets the min-height of #page-wrapper to window size
$(function() {
	$(window)
			.bind(
					"load resize",
					function() {
						var topOffset = 50;
						var width = (this.window.innerWidth > 0) ? this.window.innerWidth
								: this.screen.width;
						if (width < 768) {
							$('div.navbar-collapse').addClass('collapse');
							topOffset = 100; // 2-row-menu
						} else {
							$('div.navbar-collapse').removeClass('collapse');
						}

						var height = ((this.window.innerHeight > 0) ? this.window.innerHeight
								: this.screen.height) - 1;
						height = height - topOffset;
						if (height < 1)
							height = 1;
						if (height > topOffset) {
							$("#page-wrapper").css("min-height",
									(height) + "px");
						}
					});

	var url = window.location;
	// var element = $('ul.nav a').filter(function() {
	// return this.href == url;
	// }).addClass('active').parent().parent().addClass('in').parent();
	var element = $('ul.nav a').filter(function() {
		return this.href == url;
	}).addClass('active').parent();

	while (true) {
		if (element.is('li')) {
			element = element.parent().addClass('in').parent();
		} else {
			break;
		}
	}
	/* Login Registration Panel Hide Show */
	$('#registerNow').click(function() {
		$('#registerForm').css('display', 'block');
		$('#loginForm').css('display', 'none');
	});
	$('#signInId').click(function() {
		// alert("1");
		$('#loginForm').css('display', 'block');
		$('#registerForm').css('display', 'none');
	});
	// List and Grid List Property page
	$('#list').click(function(event) {
		event.preventDefault();
		$('#products .item').addClass('list-group-item');
	});
	$('#grid').click(function(event) {
		event.preventDefault();
		$('#products .item').removeClass('list-group-item');
		$('#products .item').addClass('grid-group-item');
	});

	// Cookies
	var setCookie = function(c_name, value, exdays) {
		var exdate = new Date();
		exdate.setDate(exdate.getDate() + exdays);
		var c_value = escape(value)
				+ ((exdays == null) ? ""
						: ("; expires=" + exdate.toUTCString()));
		document.cookie = c_name + "=" + c_value;
	}

	var getCookie = function(c_name) {
		var i, x, y, ARRcookies = document.cookie.split(";");
		for (i = 0; i < ARRcookies.length; i++) {
			x = ARRcookies[i].substr(0, ARRcookies[i].indexOf("="));
			y = ARRcookies[i].substr(ARRcookies[i].indexOf("=") + 1);
			x = x.replace(/^\s+|\s+$/g, "");
			if (x == c_name) {
				return unescape(y);
			}
		}
	}

	// Register from Local Storage
	var operation = "A"; // "A"=Adding; "E"=Editing
	var selected_index = -1; // Index of the selected list item
	var tbClients = localStorage.getItem("tbClients");// Retrieve the stored
														// data
	tbClients = JSON.parse(tbClients); // Converts string to object
	if (tbClients == null) { // If there is no data, initialize an empty
								// array
		tbClients = [];
	}
	console.log(tbClients);
	var flagDom = false;

	/*
	 * function Add(){ var client = JSON.stringify({ Name : $("#rName").val(),
	 * Age : $("#rAge").val(), Email : $("#remail").val(), Mobile :
	 * $("#rMobile").val(), Password : $("#rpassword").val() });
	 * tbClients.push(client); localStorage.setItem("tbClients",
	 * JSON.stringify(tbClients)); alert("The data was saved.");
	 * console.log(tbClients); return true; }
	 */
	// Register
	$('#rRegister').click(function() {
		// this.Add();
		var userIdNoGet = Math.random();
		var client = JSON.stringify({
			Name : $("#rName").val(),
			Age : $("#rAge").val(),
			Email : $("#remail").val(),
			Mobile : $("#rMobile").val(),
			Password : $("#rpassword").val(),
			userIdNo : userIdNoGet
		});
		tbClients.push(client);
		localStorage.setItem("tbClients", JSON.stringify(tbClients));
		alert("The data was saved.");
		console.log(tbClients);
	});
	// Login
	$('#rLogin').click(
			function() {
				console.log("Login");
				var lName = $("#lName").val();
				var lPass = $('#lPass').val();
				var count = 0;
				var userIdNo;
				for ( var i in tbClients) {
					var cli = JSON.parse(tbClients[i]);
					console.log(cli.Name);
					if (lName === cli.Name && lPass === cli.Password) {
						// alert("Enter");
						count = count + 1;
						userIdNo = cli.userIdNo;
					}
				}

				console.log(count);
				if (count > 0) {
					alert("Login Successful");
					window.location.href = "index.html?vers=" + userIdNo;
					setCookie("vars", userIdNo, "1");

					$.ajax({
						url : 'http://localhost:8082/bkcapp/property/Login/'
								+ lName + '/' + lPass + '/', // change this
																// with Post
																// property URL
																// and change
																// GET method to
																// POST with
																// data
						type : 'GET',
						// type: 'POST',
						// data: postJson,
						dataType : 'json',
						success : function(data) {
							alert("data saved");
						},
						error : function(data) {
							alert("data Not saved");
						}

					});
				} else {
					alert("Login Not Successful");
				}

			});
	// localStorage.clear();
	// dashboard user Details
	if ($('#accountPage').length > 0) {
		/*
		 * var searchParams = new URLSearchParams(window.location.search),
		 * keyval = searchParams.get('id'); console.log(keyval);
		 */
		var userLoginNo = getCookie("vars");
		console.log(userLoginNo);
		var sequenceNo;
		for ( var i in tbClients) {
			var cli = JSON.parse(tbClients[i]);
			if (cli.userIdNo == userLoginNo) {
				sequenceNo = i;
				console.log(sequenceNo);
				$("#name").val(cli.Name);
				$("#age").val(cli.Age);
				$('#uniqueId').val(userLoginNo);
				$("#emailDetailsInput").val(cli.Email);
				$("#emailDetails").html(cli.Email);
				$('#sequenceNo').val(sequenceNo), $("#mobile").val(cli.Mobile);
				$("#password1").val(cli.Password);
				$("#password2").val(cli.Password);
			}
		}
	}
	// Save Editable Account Details
	$('#accountUpdate')
			.click(
					function() {
						var name = $("#name").val(), age = $("#age").val(), emailDetails = $(
								"#emailDetailsInput").val();
						mobile = $("#mobile").val(), password = $("#password1")
								.val(), uniqueId = $('#uniqueId').val(),
								sequenceNo = $('#sequenceNo').val(),
								postJson = {
									Name : name,
									Age : age,
									Email : emailDetails,
									Mobile : mobile,
									Password : password,
									userIdNo : uniqueId,
									sequenceNo : sequenceNo
								}
						console.log(postJson);

						tbClients[sequenceNo] = JSON.stringify({
							Name : name,
							Age : age,
							Email : emailDetails,
							Mobile : mobile,
							Password : password,
							userIdNo : uniqueId
						});// Alter the selected item on the table
						localStorage.setItem("tbClients", JSON
								.stringify(tbClients));
						alert("Account has been updated Successfully");
						window.location.href = "index.html";

					})

	// Post property
	$('#propertySave')
			.click(
					function() {
						var propflatno = $('#propflatno').val(), propsize = $(
								'#propsize').val(), propowner = $('#propowner')
								.val(), propaddress = $('#propaddress').val(), propcity = $(
								'#propcity').val(), propstate = $('#propstate')
								.val(), propCompleteAddress = propaddress
								+ ", " + propcity + ", " + propstate;
						proprent = $('#proprent').val(), propdeposit = $(
								'#propdeposit').val(), propdetails = $(
								'#propdetails').val(), console.log(propdeposit)
						postJson = {
							"username" : propowner,
							"address" : propCompleteAddress,
							"aboutProperty" : propdetails,
							"rent" : proprent,
							"userEmail" : propdeposit,
							"houseSize" : propsize,
							/* "rentedState":"0", */
							"propertyRegNo" : propflatno
						}
						console.log(postJson);
						$
								.ajax({
									headers : {
										'Accept' : 'application/json',
										'Content-Type' : 'application/json'
									},
									url : 'http://localhost:8082/bkcapp/property/postProperty', // change
																								// this
																								// with
																								// Post
																								// property
																								// URL
																								// and
																								// change
																								// GET
																								// method
																								// to
																								// POST
																								// with
																								// data
									// type: 'GET',
									type : 'POST',
									data : JSON.stringify(postJson),
									dataType : 'json',
									success : function(data) {
										console.log(data)
										alert("data saved");
									},
									error : function(data) {
										alert("data Not saved");
									}

								});

					})

	// list JSON data in List property page
	var loadListData = function() {
		if ($('#products').length > 0) {
			// alert("1");
			$
					.ajax({
						url : 'http://localhost:8082/bkcapp/property/getHouses/0/', // change
																					// this
																					// with
																					// List
																					// property
																					// URL
						type : 'GET',
						dataType : 'json',
						success : function(data) {
							console.log(data);
							console.log(data.length);
							if (data && data.length > 0) {
								for (var i = 0; i <= data.length; i++) {
									// if(data[i].rentedState !== "2"){
									console.log(data[i])
									$('#products')
											.append(
													" <div class='item  col-xs-4 col-lg-4'><div class='thumbnail'><img class='group list-group-image' src='http://placehold.it/400x250/000/fff' alt=' /><div class='caption'><h4 class='group inner list-group-item-heading'>"
															+ data[i].houseSize
															+ " Sq.Ft</h4><p class='group inner list-group-item-text'>"
															+ data[i].aboutProperty
															+ "</p><div class='row'><div class='col-xs-12 col-md-6'><p class='lead'>Rs."
															+ data[i].rent
															+ "</p></div><div class='col-xs-12 col-md-6'><a class='btn btn-success testClass' href='detailPage.html?id="
															+ data[i].propertyRegNo
															+ "' id='"
															+ data[i].propertyRegNo
															+ "'>View Detail</a></div></div></div></div></div>");
									flagDom = true;
									// }
								}
							}
						},
						error : function(data) {
							console.log("error");
						}

					});
		}
	};
	loadListData();
	// Property Detail page Binding
	if ($('#productDetail').length > 0) {
		var searchParams = new URLSearchParams(window.location.search), keyval = searchParams
				.get('id');
		console.log(keyval);
		$.ajax({
			url : 'http://localhost:8082/bkcapp/property/gethouseDetails/'
					+ keyval + '/', // change this with Property Detail URL
			type : 'GET',
			dataType : 'json',
			success : function(data) {
				console.log(data);
				if (data.hasOwnProperty("username")
						&& data.propertyRegNo === keyval) {
					// fill the input field
					$('#propflatno').val(data.propertyRegNo);
					$('#bpropflatno').val(data.propertyRegNo);
					$('#propsize').val(data.houseSize);
					$('#propowner').val(data.username);
					$('#propaddress').val(data.address);
					$('#proprent').val(data.rent);
					$('#bproprent').val(data.rent);
					$('#propdeposit').val(data.userEmail);
					$('#propdetails').val(data.aboutProperty);
					$('#propPostedDate').val(data.postedTimeStampFormatted);

					// Fill The Display
					$('.propflatno').html(data.propertyRegNo);
					$('.propsize').html(data.houseSize);
					$('.propowner').html(data.username);
					$('.propaddress').html(data.address);
					$('.proprent').html(data.rent);
					$('.propdeposit').html(data.userEmail);
					$('.propdetails').html(data.aboutProperty);
					$('.propPostedDate').html(data.postedTimeStampFormatted);
					console.log("user  " + data.isCurrentUserOwner);
					if (data.isCurrentUserOwner === true) {
						console.log("If");
						$("#requestModalbtn").hide();
						$("#createAgreementModalbtn").show();
					} else {
						console.log("else");
						$("#createAgreementModalbtn").hide();
						$("#requestModalbtn").show();
					}
				}
			},
			error : function(data) {
				console.log("error");
			}
		});
	}

	// Confirm Booking
	$('#confirmBooking')
			.click(
					function() {
						var bpropflatno = $('#bpropflatno').val(), 
								bproprent = $('#bproprent').val(), 
								tenentAddr = $('#tenentAddr').val(), 
								tenantEmail = $('#tenantEmail').val()
						postJson = {
							"rent" : bproprent,
							"propertyRegNo" : bpropflatno,
							"tenant" : tenentAddr,
							"tenantEmail" : tenantEmail
						}
						console.log(postJson);

						$
								.ajax({
									url : 'http://localhost:8082/bkcapp/property/createAgreement', 
									headers : {
										'Accept' : 'application/json',
										'Content-Type' : 'application/json'
									},
									// type: 'GET',
									type : 'POST',
									data : JSON.stringify(postJson),
									dataType : 'json',
									success : function(data) {
										alert("data saved");
										// $('.alert-success').css('display','block');
									},
									error : function(data) {
										alert("data Not saved");
										// $('.alert-danger').css('display','block');
									}

								});

					})

	$('#requestForRentModal')
			.click(
					function() {

						var userLoginNo = getCookie("vars");
						console.log(userLoginNo);

						var usernamePJ, userEmailPJ, mobileNoPJ;

						for ( var i in tbClients) {
							var cli = JSON.parse(tbClients[i]);
							if (cli.userIdNo == userLoginNo) {
								sequenceNo = i;
								console.log(sequenceNo);
								usernamePJ = cli.Name;
								userEmailPJ = cli.Email;
								mobileNoPJ = cli.Mobile;

							}
						}

						var bpropflatno = $('#bpropflatno').val(), bproprent = $(
								'#bproprent').val(), userEmail = $(
								'#propdeposit').val();
						postJson = {
							userName : usernamePJ,
							userEmail : userEmailPJ,
							userMobileNo : mobileNoPJ,
							propertyRegNo : bpropflatno,
							ownerEmail : userEmail

						}

						console.log(postJson);
						$
								.ajax({
									url : 'http://localhost:8082/bkcapp/property/requestForRent',
									headers : {
										'Accept' : 'application/json',
										'Content-Type' : 'application/json'
									},// change this with Book property URL
										// and change GET method to POST with
										// data
									// type: 'GET',
									type : 'POST',
									data : JSON.stringify(postJson),
									dataType : 'json',
									success : function(data) {
										alert("data saved");
										// $('.alert-success').css('display','block');
									},
									error : function(data) {
										alert("data Not saved");
										// $('.alert-danger').css('display','block');
									}

								});

					})

});