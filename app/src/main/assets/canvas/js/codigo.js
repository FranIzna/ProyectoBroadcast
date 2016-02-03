window.addEventListener("load", function(){
		var entero=getRandomInt(0,3);

		var tipo="";
		if(entero==0)
			tipo: "bar";
        else if (entero==1)
        	tipo: "area";
        else if (entero==2)
        	tipo:"spline";
        else tipo:"pie";

        var chart = new CanvasJS.Chart("chartContainer", {
		theme: "theme2",//theme1
		title:{
			text: "Grafico"
		},
		animationEnabled: false,   // change to true
		data: [              
		{
			// Change type to "bar", "area", "spline", "pie",etc.

			type:"pie",

			dataPoints: [
				{ label: "Lunes",    y: InterfazAndroid.enviarDia(0)},
				{ label: "Martes",   y: InterfazAndroid.enviarDia(1)},
				{ label: "Miercoles",y: InterfazAndroid.enviarDia(2)},
				{ label: "Jueves",   y: InterfazAndroid.enviarDia(3)},
				{ label: "Viernes",  y: InterfazAndroid.enviarDia(4)},
				{ label: "Sabado",   y: InterfazAndroid.enviarDia(5)},
				{ label: "Domingo",  y: InterfazAndroid.enviarDia(6)}
			]
		}
		]
	});
	chart.render();
	function getRandomInt(min, max) {
      return Math.floor(Math.random() * (max - min)) + min;
    }
});

