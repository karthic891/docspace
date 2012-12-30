function focusLogin() {
	document.getElementById('mainForm:emailText').focus();
}

function handleKeyPress(event) {
	if(event.keyCode == 13) {
		document.getElementById('mainForm:loginBtn').click();
	}
}

function displayChangePicIcon() {
		var ids = document.getElementById('changePic');
		ids.style.visibility = "visible";
}

function hideChangePicIcon() {
	var ids = document.getElementById('changePic');
	ids.style.visibility = "hidden";
}