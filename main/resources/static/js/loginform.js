$(function() {

// Initialize form validation on the registration form.
  // It has the name attribute "registration"
  $('#login-form').validate({
    // Specify validation rules
    rules: {
      // The key name on the left side is the name attribute
      // of an input field. Validation rules are defined
      // on the right side
      email: {
        required: true,
        // Specify that email should be validated
        // by the built-in "email" rule
        email: true
      },
      password: "required"
    },
    // Specify validation error messages
    messages: {
      email: "Please enter a valid email address",
      password: "Please enter your username"
    },
    // Make sure the form is submitted to the destination defined
    // in the "action" attribute of the form when valid
    submitHandler: function(form) {
          $.ajax({
     type: "POST",
      url: "user/login",
      contentType : 'application/json',
      data : JSON.stringify({
        "emailID" : $("#email").val(),
        "password" :$("#password").val()
      }),
      success: function() {
        //window.location='home.html';
        window.location.replace ('home.html');
       },
       error: function(xhr, text, error) {
      alert('Error: ' + xhr.responseJSON.message);
   }
    })
    }
  });

$('#register-form').validate({
    // Specify validation rules
    rules: {
      // The key name on the left side is the name attribute
      // of an input field. Validation rules are defined
      // on the right side
      username: "required",
      email: {
        required: true,
        // Specify that email should be validated
        // by the built-in "email" rule
        email: true
      },
      password1: "required",
      password2: {
            required: true,
            equalTo: "#password1"
        }
    },
    // Specify validation error messages
    messages: {
      username: "Please enter your username",
      password1: "Please enter your password",
      email: "Please enter a valid email address",
      password2: "Please re-enter your password correctly",
    },
    // Make sure the form is submitted to the destination defined
    // in the "action" attribute of the form when valid
    submitHandler: function(form) {
          $.ajax({
     type: "POST",
      url: "user/register",
      contentType : 'application/json',
      data : JSON.stringify({
        "emailID" : $("#emailID").val(),
        "password" :$("#password1").val(),
        "name" :$("#username").val()
      }),
      success: function() {
       // window.location='home.html';
         window.location.replace ('home.html');
       
       },
       error: function(xhr, text, error) {
      alert('Error: ' + xhr.responseJSON.message);
   }
    })
    }
  });


    $('#login-form-link').click(function(e) {
		$("#login-form").delay(100).fadeIn(100);
 		$("#register-form").fadeOut(100);
		$('#register-form-link').removeClass('active');
		$(this).addClass('active');
		e.preventDefault();
	});
	$('#register-form-link').click(function(e) {
		$("#register-form").delay(100).fadeIn(100);
 		$("#login-form").fadeOut(100);
		$('#login-form-link').removeClass('active');
		$(this).addClass('active');
		e.preventDefault();
	});

});
