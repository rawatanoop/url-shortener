$(function() {

	$('#logoutButton').click(function(e) {
          $.ajax({
     type: "GET",
      url: "user/logout",
      success: function() {
         window.location.replace ('index.html');
       
       },
       error: function(xhr, text, error) {
      alert('Error: ' + xhr.responseText);
   }
    })
    });
	});

