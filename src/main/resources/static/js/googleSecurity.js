
function onSignIn(googleUser) {
    // Useful data for your client-side scripts:
    var profile = googleUser.getBasicProfile();
     var email = profile.getEmail().toString();
     
    if(!validateEmail(email)){
    	$("#loginModal").modal('show');
    	gapi.auth2.getAuthInstance().disconnect();
    	signOut();
    	return;
    }
   
    // The ID token you need to pass to your backend:
    var id_token = googleUser.getAuthResponse().id_token;

    if (sessionStorage.getItem("userObj") == null) {
        var xhr = new XMLHttpRequest();
        xhr.open('POST', '/tokensignin');
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        xhr.onload = function() {
        	
        	
        	if(xhr.responseText == "/login"){
        		console.log('fraud login');
            	gapi.auth2.getAuthInstance().disconnect();
            	signOut();
            	return;
        	}
        	else{
        		sessionStorage.setItem("userObj",profile.getEmail().toString());
                sessionStorage.setItem("name",profile.getName().toString());
                SetUserProfile();
                //on successful, adding the socket to the list
                socket.emit("add user",profile.getEmail().toString());
        	}
        };
        xhr.send('idtoken=' + id_token);

    }
    
};

function validateEmail(email){
    return email.match(/^\"?[\w-_\.]*\"?@snapdeal\.com$/);        
}

function SetUserProfile(){
 	if(sessionStorage.getItem("userObj") == null)
	{
		$(".g-signin2").removeClass("hide");
		$("#logout").addClass("hide");
		$(".profile").text("");
	}	
	else{
		$(".g-signin2").addClass("hide");
		$("#logout").removeClass("hide");
		var name = sessionStorage.getItem("name");
		$(".profile").text(name);
	}
	}



function signOut() {
    var auth2 = gapi.auth2.getAuthInstance();
    var emailId =  sessionStorage.getItem("userObj").toString();
    
    sessionStorage.removeItem("userObj");
    sessionStorage.removeItem("name");
   
    socket.emit("disconnect user",emailId);
    
    auth2.signOut().then(function() {
        console.log('User signed out.');
        console.log(auth2);
        auth2.disconnect();
        var xhr = new XMLHttpRequest();
        xhr.open('POST', '/tokensignout');
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        xhr.onload = function() {
            SetUserProfile();
        };
        xhr.send();
       
    });
   
    
}
