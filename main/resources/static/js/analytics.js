
function createRow(data,i,item){

     tr = $('<tr/>');
      tr.append("<td> <a href = '" +item.longURL+"'>"+item.longURL+"</a></td> <td> <a href = '" +item.urlPrefix+item.shortURL+"'>"+item.shortURL+"</a></td> ");
      //tr.append("<button class="btn btn-primary"  > <span class="glyphicon glyphicon-equalizer"></span> </button>");

      tr.append($('<button/>', { 
      id: "shortButton_"+item.id ,
      class:"glyphicon glyphicon-equalizer",

      click: function() { 

        $.ajax({
          url : 'analytics/'+item.shortURL,
          type : 'GET',
          success: function(anlytic_data, textStatus, xhr) {
            $('#clicksText').text("Total Clicks : "+anlytic_data.totalCount);

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
}

