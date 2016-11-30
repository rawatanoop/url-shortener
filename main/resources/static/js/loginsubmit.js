$(document).ready(function() {

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
