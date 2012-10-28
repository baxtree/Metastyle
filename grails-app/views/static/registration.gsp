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
	<g:if test="${ flash.message }">
    	<span class="warning span3">${ flash.message }</span>
    </g:if>	
	<section id="main-container">
		    <g:form class="form-horizontal offset3" name="userRegister" url='[controller: "user",  action:"userRegister"]'>
		    	<div class="control-group">
			    	<label class="control-label" for="username">Username</label>
			    	<div class="controls">
			    		<input type="text" id="username" name="username" placeholder="8 - 12 characters">
			   		</div> 
			   	</div>
			   	<div class="control-group">
			    	<label class="control-label" for="fullname">Full Name</label>
			    	<div class="controls">
			    		<input type="text" id="fullname" name="fullname" placeholder="First Last">
			   		</div> 
			   	</div>
			    <div class="control-group">
			    	<label class="control-label" for="email">Email</label>
			    	<div class="controls">
			    		<input type="text" id="email" name="email" placeholder="Email">
			    	</div>
			    </div>
			    <div class="control-group">
			    	<label class="control-label" for="password">Password</label>
			    	<div class="controls">
			    		<input type="password" id="password" name="password" placeholder="Password">
			    	</div>
			    </div>
			    <div class="control-group">
			    	<label class="control-label" for="repassword">Repeat</label>
			    	<div class="controls">
			    		<input type="password" id="repassword" name="repassword" placeholder="Password again">
			    	</div>
			    </div>
			    <div class="control-group">
			    	<div class="controls">
			    		<button type="submit" class="btn">Register</button>
			    	</div>
			    </div>
    		</g:form>
	</section>
<g:render template="../templates/footer"></g:render>