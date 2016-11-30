

$(document).ready(function(){    
  $.get("api/list" ,function(data, status){
    $.each(data, function (i, item) {
     tr = $('<tr/>');
      tr.append("<td> <a href = '"+$('#longUrlText').val()+"'>"+data[i].longURL+"</a></td> <td>"+data[i].shortURL+
      "</td> ");
      //tr.append("<button class="btn btn-primary"  > <span class="glyphicon glyphicon-equalizer"></span> </button>");

      tr.append($('<button/>', { 
      id: "shortButton_"+data[i].id ,
      class:"glyphicon glyphicon-equalizer",

      click: function() { 

        $.ajax({
          url : 'analytics/'+data[i].shortURL,
          type : 'GET',
          success: function(data, textStatus, xhr) {

            var mymodal = $('#myModal');
              mymodal.find('.modal-title').text("Analytics for long-url : "+data.longUrl);
              mymodal.find('.modal-body').text("For short-url  \""+data.shortUrl+"\" total clicks are : " + data.totalCount);
              mymodal.modal('show');
       },
       error: function(xhr, text, error) {
        alert('Error: ' + xhr.responseText);
   }

        });
      }
    }));
                
      $('#urlTable').append(tr);
    }); 
  });

$('#shortButton').click(function(){
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
                $("#urlTable").append("<tr> <td> <a href = '"+$('#longUrlText').val()+"'>"+$('#longUrlText').val()+
                  "</a></td> <td>"+data+"</td> <td> <a href='logout'><span class='glyphicon glyphicon-equalizer'></span> </a> </td> </tr>");
                
              }
              
        $('#longUrlText').val('');
       },
       error: function(xhr, text, error) {
      alert('Error: ' + xhr.responseText);
   }

        });
});


});      

