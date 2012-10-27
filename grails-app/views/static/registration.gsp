<g:render template="../templates/header" />
<g:render template="../templates/banner" />
	<section id="menu-bar">
		<div class="container">
			<div class="row">
				<div class="span6">
					<ul class="navbar navbar-inverse navbar-fixed-top">
						<div class="navbar-inner">
					        <div class="container">
					          <g:link class="brand" controller="static" action="welcome">CSSMetaSelector</g:link>
					          <div class="nav-collapse collapse">
					            <ul class="nav">
					              <li class="">
					                <g:link controller="static" action="welcome">Home</g:link>
					              </li>
					              <li class="">
					                <g:link controller="static" action="getSkeletons">Get Skeletons</g:link>
					              </li>
					              <li class="">
					                <g:link controller="user" action="showTemplates">My Templates</g:link>
					              </li>
					            </ul>
					            <g:render template="../templates/authentication" />
					          </div>
					        </div>
					    </div>
					</ul>
				</div>
			</div>
		</div>
	</section>
	<section id="main-container">
		    <form class="form-horizontal offset3">
		    	<div class="control-group">
			    	<label class="control-label" for="username">Username</label>
			    	<div class="controls">
			    		<input type="text" id="username" placeholder="8 - 12 characters">
			   		</div> 
			   	</div>
			    <div class="control-group">
			    	<label class="control-label" for="inputEmail">Email</label>
			    	<div class="controls">
			    		<input type="text" id="inputEmail" placeholder="Email">
			    	</div>
			    </div>
			    <div class="control-group">
			    	<label class="control-label" for="inputPassword">Password</label>
			    	<div class="controls">
			    		<input type="password" id="inputPassword" placeholder="Password">
			    	</div>
			    </div>
			    <div class="control-group">
			    	<label class="control-label" for="rePassword">Repeat</label>
			    	<div class="controls">
			    		<input type="password" id="rePassword" placeholder="Password again">
			    	</div>
			    </div>
			    <div class="control-group">
			    	<div class="controls">
			    		<button type="submit" class="btn">Register</button>
			    	</div>
			    </div>
    		</form>
	</section>
<g:render template="../templates/footer"></g:render>