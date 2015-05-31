function makeImageHtml(productName, used) {
	var color="white";
	if (used === "yes") color = "green";
	else if (used === "no") color = "red";
	else if (used === "couldhave") color = "yellow";
	return '<img height="24" width="24" border="0" align="middle" src="./images/' + productName + '.png" style="vertical-align: middle; background-color: ' + color + '">';
}

(function (){
	if( typeof window.addEventListener === 'function' ) {
		Reveal.addEventListener('ready', function() {

		var logomarker_nodes = document.querySelectorAll( 'div.logomarker, span.logomarker' );

		var products = [ 'docker', 'mesos' ];

		for( var i = 0, len = logomarker_nodes.length; i < len; i++ ) {
			var element = logomarker_nodes[i];
			var contents = "";

			for (var pi in products) {

				var product = products[pi];
			
				if( element.hasAttribute( product ) ) {
					contents += makeImageHtml( product, element.getAttributeNode( product ).value );
				}
			}
			element.innerHTML = contents;
		}
		});
	}
})();

