
function openModal(myBtnId){
	console.log("click");
	// Get the modal
	let modal = document.getElementById("myModal"+myBtnId);
	console.log(modal);
	// Get the button that opens the modal
	let btn = document.getElementById(myBtnId);
	
	// Get the <span> element that closes the modal
	let span = document.getElementById("close"+myBtnId);
	console.log(span);
	
	modal.style.display = "block";
	
	// When the user clicks on <span> (x), close the modal
	span.onclick = function() {
	  modal.style.display = "none";
	}
	
	// When the user clicks anywhere outside of the modal, close it
	window.onclick = function(event) {
	  if (event.target == modal) {
	    modal.style.display = "none";
	  }
	}
}


