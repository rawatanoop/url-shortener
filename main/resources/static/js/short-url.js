

$(document).ready(function(){    
  $.get("api/list" ,function(data, status){
    $.each(data, function (i, item) {
      createRow(data,i,item);
     });

$('#shortButton').click(function(){
  var textVal = $('#longUrlText').val();
 
  if(textVal=='' || textVal.trim()==''){
    var mymodal = $('#myModal');
              mymodal.find('.modal-title').text("Long URl is empty");
              mymodal.find('.modal-body').text("Please enter a valid URL");
              mymodal.modal('show');
    return ;
  }
  $.ajax({
          url : 'api/shortener',
          type : 'POST',
          contentType : 'application/json',
          data : JSON.stringify({
            "longURL" : $('#longUrlText').val()
          }),
          success: function(data, textStatus, xhr) {

            var mymodal = $('#myModal');
              mymodal.find('.modal-title').text("Shorted Url created successfully!");
              mymodal.find('.modal-body').text(data);
              mymodal.modal('show');
              if(xhr.status==201){  

              createRow(null,0,{longURL:$('#longUrlText').val(),shortURL:data});             
                
              }
              
        $('#longUrlText').val('');
       },
       error: function(xhr, text, error) {
      alert('Error: ' + xhr.responseText);
   }

        });
});
});
});
    

