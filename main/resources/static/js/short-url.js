

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
          success: function(anlytic_data, textStatus, xhr) {

  var browserChart = new CanvasJS.Chart("browserChartContainer",
  {
    title:{
      text: "Browser Chart",
      verticalAlign: 'top',
      horizontalAlign: 'center'
    },
                animationEnabled: true,
    data: [
    {        
      type: "doughnut",
      startAngle:20,
      toolTipContent: "{label}: <strong>#percent%</strong> - {y} clicks",
      indexLabel: "{label}  #percent%",
      dataPoints: [ ]
    }
    ]
  });
  $.each(anlytic_data.browsersAnytcs, function (i, item) {
        browserChart.options.data[0].dataPoints.push({y: item.count,label: item.name});
      });
  
  browserChart.render();  


  

  var refererChart = new CanvasJS.Chart("refererChartContainer",
    {
      title:{
        text: "Referer Chart"    
      },
      animationEnabled: true,
      axisY: {
        title: "Nuber of Clicks"
      },
      legend: {
        verticalAlign: "bottom",
        horizontalAlign: "center"
      },
      theme: "theme2",
      data: [

      {        
        type: "column",  
        showInLegend: true, 
        legendMarkerColor: "grey",
        legendText: "Referes : Source of visits",
        dataPoints: []
      }   
      ]
    });
  $.each(anlytic_data.refererAnytcs, function (i, item) {
        refererChart.options.data[0].dataPoints.push({y: item.count,label: item.name});
      });
    refererChart.render();       

var locationChart = new CanvasJS.Chart("countryChartContainer",
  {
     title:{
       text: "Country Chart"
     },
      animationEnabled: true,
    legend:{
      verticalAlign: "center",
      horizontalAlign: "left",
     // fontSize: 20,
      fontFamily: "Helvetica"        
    },
    theme: "theme2",
    data: [
    {        
      type: "pie",       
      indexLabelFontFamily: "Garamond",       
      //indexLabelFontSize: 20,
      indexLabel: "{label} {y} Clicks",
      startAngle:-20,      
      showInLegend: true,
      toolTipContent:"{legendText} {y}",
      dataPoints: [
       // {  y: 83.24, legendText:"R", label: "R" },

      ]
    }
    ]
  });
$.each(anlytic_data.locationAnytcs, function (i, item) {
        locationChart.options.data[0].dataPoints.push({y: item.count,legendText:item.name ,label: item.name});
      });
  locationChart.render();



              
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

