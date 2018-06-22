  var usertoken = "";
  window.onload = function() {
    document.querySelector('button').addEventListener('click', function() {
      chrome.identity.getAuthToken({interactive: true}, function(token) {
        console.log(token);
        usertoken = token;
      });
      chrome.identity.getProfileUserInfo(function(info) {
      	console.log(info.email);
      	console.log(info.id);
      });   
    });
  };
  