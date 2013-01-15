<section id="main-container">
	    <g:form class="form-horizontal offset3" name="userLogin" controller="user" action="userLogin">
		    <div class="control-group">
		    <label class="control-label" for="username">Username</label>
		    <div class="controls">
		    <input type="text" id="username" name="username" placeholder="Username">
		    </div>
		    </div>
		    <div class="control-group">
		    <label class="control-label" for="password">Password</label>
		    <div class="controls">
		    <input type="password" id="password" name="password" placeholder="Password">
		    </div>
		    </div>
		    <div class="control-group">
		    <div class="controls">
		    <button type="submit" class="btn">Sign in</button>
		    </div>
		    </div>
   		</g:form>
   		<hr>
		<div class="github-button span3 offset5">
			<g:link controller="static" action="signInWithGithub"><span class="github-signin"><i class="icon-github"></i>&nbsp;Sign in with Github</span></g:link>
		</div>
		<div class="google-button span4 offset5">
			<g:link controller="static" action="signInWithGoogle"><span class="google-signin"><i class="icon-google-plus-sign"></i>&nbsp;Sign in with Google</span></g:link>
		</div>
</section>