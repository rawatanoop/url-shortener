$(document).ready(function() {
  $("#login-form").submit(function() {

    $.ajax({
     type: "POST",
      url: "user/login",
      contentType : 'application/json',
      data : JSON.stringify({
        "emailID" : $("#email").val(),
        "password" :$("#password").val()
      }),
      success: function() {
        alert("done");
       },
       error: function(xhr, text, error) {
      alert('Error: ' + xhr.responseText);
   }
    })

  });

  $("#register-form").submit(function() {

    $.ajax({
     type: "POST",
      url: "user/register",
      contentType : 'application/json',
      data : JSON.stringify({
        "emailID" : $("#email").val(),
        "password" :$("#password1").val(),
        "name" :$("#username").val()
      }),
      success: function() {
        alert("done");
       },
       error: function(xhr, text, error) {
      alert('Error: ' + xhr.responseText);
   }
    })

  });
})
