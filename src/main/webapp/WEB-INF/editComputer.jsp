<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../include/header.jsp" />
<script type="text/javascript" src="lib/jquery.js"></script>
<script type="text/javascript" src="dist/jquery.validate.js"></script>

<script type="text/javascript">
	$(document).ready(
			function() {
				jQuery.validator.addMethod("dateFormat", function(value,
						element) {
					var re = /^\d{4}-\d{1,2}-\d{1,2}$/;
					return (this.optional(element) && value == "")
							|| re.test(value);
				}, jQuery.validator.format("Incorrect format"));
				
				jQuery.validator.addMethod("checkDate", function(value,
						element) {
					var elem = value.split('-');
					var day = parseInt(elem[2]);
					var month = parseInt(elem[1]);
					var year = parseInt(elem[0]);
					if (day == 31 
						&& (month == 4 || month == 6 || month == 9 ||
							month == 11 || month == 04 || month == 06 ||
							month == 09)
						) {
						return false; // only 1,3,5,7,8,10,12 has 31 days
					} else if (month == 2) { // fevrier
						// année bissextile
						if(year % 4==0){
							if(day == 30 || day == 31){
								return false;
							}else{
								return true;
							}
						}else{
							if(day == 29||day == 30||day == 31){
								return false;
							}else{
								return true;
							}
						}
					}else{				 
						return true;				 
					}
				}, "This date is not valid");

				jQuery.validator.addMethod("endDate", function(value, element) {
					var startDate = $('#introducedDate').val();
					if ($.trim(value).length > 0)
						return Date.parse(startDate) <= Date.parse(value);
					else
						return true;
				}, "Discontinued date must be after introduced date");

				jQuery.validator.addMethod("requireIntroduced", function(value,
						element) {
					var startDate = $('#introducedDate').val();
					if ($.trim(value).length > 0)
						return $.trim(startDate).length > 0;
					else
						return true;
				}, "Discontinued date requires an introduced date first");

				jQuery(document).ready(function() {
					jQuery("#editComputerForm").validate({
						highlight : function(element, errorClass) {
							$(element).fadeOut(function() {
								$(element).fadeIn();
							});
						},
						rules : {
							"name" : {
								"required" : true,
								"maxlength" : 255
							},
							"introducedDate" : {
								dateFormat : true,
								checkDate : true
							},
							"discontinuedDate" : {
								dateFormat : true,
								checkDate : true,
								requireIntroduced : true,
								endDate : true
							}
						}
					});
				});
			});
</script>

<section id="main">

	<h1>Edit Computer</h1>

	<form id="editComputerForm" action="EditComputerServlet" method="POST">
		<input type="hidden" name="id" value="${ computer.id }">
		<fieldset>
			<div class="clearfix">
				<label for="name">Computer name:</label>
				<div class="input-group">
					<input class="form-control" type="text" id="name" name="name"
						value="${computer.name}" /> <span class="input-group-addon">Required</span>
				</div>
			</div>

			<div class="clearfix">
				<label for="introduced">Introduced date:</label>
				<div class="input-group">
					<input class="form-control" type="date" id="introducedDate"
						name="introducedDate"
						value="${computer.introducedDate.toString('yyyy-MM-dd')}"
						pattern="\d{4}-\d{1,2}-\d{1,2}" /> <span
						class="input-group-addon">YYYY-MM-DD</span>
				</div>
			</div>
			<div class="clearfix">
				<label for="discontinued">Discontinued date:</label>
				<div class="input-group">
					<input class="form-control" type="date" id="discontinuedDate"
						name="discontinuedDate"
						value="${computer.discontinuedDate.toString('yyyy-MM-dd')}"
						pattern="\d{4}-\d{1,2}-\d{1,2}" /> <span
						class="input-group-addon">YYYY-MM-DD</span>
				</div>
			</div>
			<div class="clearfix">
				<label for="company">Company Name:</label>
				<div class="input-group">
					<select name="company">
						<option value="0">--</option>
						<c:forEach items="${ companyList }" var="comp">

							<c:choose>
								<c:when test="${ comp.id == computer.company.id }">
									<option value="${ comp.id }" selected><c:out
											value="${ comp.name }" /></option>
								</c:when>
								<c:otherwise>
									<option value="${ comp.id }"><c:out
											value="${ comp.name }" /></option>
								</c:otherwise>
							</c:choose>

						</c:forEach>
					</select>
				</div>
			</div>
		</fieldset>
		<div class="actions">
			<input type="submit" value="Edit" class="btn btn-info"> or <a
				href="RedirectIndexServlet" class="btn btn-default">Cancel</a>
		</div>
	</form>
	<p id="msg_err">
		
		<c:forEach var="elt" items="${errorList}">
		    <c:choose>
			    <c:when test="${((elt.key == 'name')&&(elt.value==1))}">Name must not be empty.<br/></c:when>
			    <c:when test="${((elt.key == 'introducedDate')&&(elt.value==1))}">Introduced date must not be empty.<br/></c:when>
			    <c:when test="${((elt.key == 'introducedDate')&&(elt.value==2))}">Introduced date is in the wrong format.<br/></c:when>
			    <c:when test="${((elt.key == 'discontinuedDate')&&(elt.value==2))}">Discontinued date is in the wrong format.<br/></c:when>
			    <c:when test="${((elt.key == 'discontinuedDate')&&(elt.value==3))}">Discontinued date must be posterior than introduced date.<br/></c:when>
			    <c:otherwise>Champ inconnu.<br/></c:otherwise>
			</c:choose>
		</c:forEach>
	</p>
</section>

<jsp:include page="../include/footer.jsp" />