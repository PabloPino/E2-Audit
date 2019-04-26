<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<security:authorize access="hasRole('ADMIN')">


	<fieldset>
		<legend>
			<spring:message code="dashboard.queryC1">
			</spring:message>
		</legend>
		<acme:out code="dashboard.queryC1AVG" value="${queryC1AVG}" />
		<acme:out code="dashboard.queryC1MAX" value="${queryC1MAX}" />
		<acme:out code="dashboard.queryC1MIN" value="${queryC1MIN}" />
		<acme:out code="dashboard.queryC1STDDEV" value="${queryC1STDDEV}" />
	</fieldset>

	<fieldset>
		<legend>
			<spring:message code="dashboard.queryC2">
			</spring:message>
		</legend>
		<acme:out code="dashboard.queryC2AVG" value="${queryC2AVG}" />
		<acme:out code="dashboard.queryC2MAX" value="${queryC2MAX}" />
		<acme:out code="dashboard.queryC2MIN" value="${queryC2MIN}" />
		<acme:out code="dashboard.queryC2STDDEV" value="${queryC2STDDEV}" />
	</fieldset>

	<fieldset>
		<legend>
			<spring:message code="dashboard.queryC3">
			</spring:message>
		</legend>
		<display:table name="queryC3" id="row" requestURI="${requestURI}"
			pagesize="5" class="displaytag">
			<acme:column code="dashboard.queryC3Company" value="${row}" />
		</display:table>
	</fieldset>
	
	<fieldset>
		<legend>
			<spring:message code="dashboard.queryC4">
			</spring:message>
		</legend>
		<display:table name="queryC4" id="row" requestURI="${requestURI}"
			pagesize="5" class="displaytag">
			<acme:column code="dashboard.queryC4Hacker" value="${row}" />
		</display:table>
	</fieldset>
	
	<fieldset>
		<legend>
			<spring:message code="dashboard.queryC5">
			</spring:message>
		</legend>
		<acme:out code="dashboard.avgC5" value="${avgC5}" />
		<acme:out code="dashboard.maxC5" value="${maxC5}" />
		<acme:out code="dashboard.minC5" value="${minC5}" />
		<acme:out code="dashboard.stddevC5" value="${stddevC5}" />
	</fieldset>
	
	<fieldset>
		<legend>
			<spring:message code="dashboard.queryC6">
			</spring:message>
		</legend>
		<acme:out code="dashboard.queryC6Best" value="${queryC6Best}" />
		<acme:out code="dashboard.queryC6Worst" value="${queryC6Worst}" />
		
	</fieldset>
	
	<fieldset>
		<legend>
			<spring:message code="dashboard.queryB1">
			</spring:message>
		</legend>
		<acme:out code="dashboard.queryB1AVG" value="${queryB1AVG}" />
		<acme:out code="dashboard.queryB1MAX" value="${queryB1MAX}" />
		<acme:out code="dashboard.queryB1MIN" value="${queryB1MIN}" />
		<acme:out code="dashboard.queryB1STDDEV" value="${queryB1STDDEV}" />
	</fieldset>
	
	<fieldset>
		<legend>
			<spring:message code="dashboard.queryB2">
			</spring:message>
		</legend>
		<acme:out code="dashboard.queryB2AVG" value="${queryB2AVG}" />
		<acme:out code="dashboard.queryB2MAX" value="${queryB2MAX}" />
		<acme:out code="dashboard.queryB2MIN" value="${queryB2MIN}" />
		<acme:out code="dashboard.queryB2STDDEV" value="${queryB2STDDEV}" />
	</fieldset>
	
	<fieldset>
		<legend>
			<spring:message code="dashboard.queryB3">
			</spring:message>
		</legend>
		<acme:out code="dashboard.queryB3Ratio" value="${queryB3}" />
		
	</fieldset>
</security:authorize>