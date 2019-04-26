<%--
 * edit.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>


<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="${requestURI}" modelAttribute="application">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="problem" />
	<form:hidden path="position" />
	<form:hidden path="hacker" />



	<security:authorize access="hasRole('HACKER')">
		<jstl:if test="${application.status.equals('PENDING')}">
			<form:hidden path="status" />
		</jstl:if>
		<%-- <jstl:if test="${application.answerCode.isEmpty() || application.answerExplanation.isEmpty()}"> --%>
		<jstl:if test="${isRead == false}">
			<acme:textbox code="app.answerExplanation" path="answerExplanation" />
			<br>
			<acme:textbox code="app.answerCode" path="answerCode" />
			<br>
			<form:label path="curricula">
				<spring:message code="app.curricula"></spring:message>
			</form:label>
			<form:select path="curricula">
				<jstl:forEach items="${pds}" var="pds">
					<form:option label="${pds.statement}" id="${pds.curricula.id}"
						value="${pds.curricula.id}" />
				</jstl:forEach>
			</form:select>
			<%-- <form:select id="curricula" path="curricula">
				<form:options items="${curriculas}" itemLabel="${statements}"
						itemValue="${statements}" />
				<form:options items="${curriculas}" itemLabel="id"
						itemValue="id" />
			</form:select> --%>
			<br>
			<br>
			<%-- 			<acme:select items="${curriculas}" itemLabel="${curriculas}" code="app.curricula" path="curricula"/>--%>
		</jstl:if>


	</security:authorize>
	<jstl:if test="${isRead == true}">
		<acme:textbox code="app.answerExplanation" path="answerExplanation" />
		<br>
		<acme:textbox code="app.answerCode" path="answerCode" />
		<br>
		<acme:textbox code="app.publishMoment" path="publishMoment" />
		<br>
		<acme:textbox code="app.submitMoment" path="submitMoment" />
		<br>
		<%-- <acme:textbox code="app.curricula" path="${pd.statement}" /> --%>
		<form:label path="curricula">
			<spring:message code="app.curricula"></spring:message>
		</form:label>
		<form:select path="curricula">
			<jstl:forEach items="${pds}" var="pds">
				<form:option label="${pds.statement}" id="${pds.curricula.id}"
					value="${pds.curricula.id}" />
			</jstl:forEach>
		</form:select>
		<br>
		<br>
		<acme:textbox code="app.problem" path="problem.title" />
		<br>
		<acme:textbox code="app.position" path="position.title" />
		<br>
		<acme:textbox code="app.company" path="position.company.comercialName" />
		<br>
		<acme:textbox code="app.hacker" path="hacker.name" />
		<br>
	</jstl:if>
	<security:authorize access="hasRole('HACKER')">
		<jstl:if
			test="${application.id !=0  && !application.answerCode.isEmpty() && !application.answerExplanation.isEmpty() && application.status.equals('PENDING')}">
			<form:label path="status">
				<spring:message code="app.status"></spring:message>
			</form:label>
			<form:select id="status" path="status">
				<option value="SUBMITTED">SUBMITTED</option>
			</form:select>
			<br>
			<br>
		</jstl:if>
	</security:authorize>
	<security:authorize access="hasRole('COMPANY')">
		<%-- <form:hidden path="publishMoment" />
		<form:hidden path="submitMoment" /> --%>
		<%-- <form:hidden path="answerExplanation" />
		<form:hidden path="answerCode" /> --%>
		<jstl:if test="${application.status.equals('SUBMITTED')}">
			<form:label path="status">
				<spring:message code="app.status"></spring:message>
			</form:label>
			<form:select id="status" path="status">
				<option value="ACCEPTED">ACCEPTED</option>
				<option value="REJECTED">REJECTED</option>
			</form:select>
		</jstl:if>
		<br>
		<br>
	</security:authorize>
	<jstl:if test="${isRead == false}">

		<%-- <input type="submit" name="save"
			value="<spring:message code="app.save" />" /> --%>
		<acme:submit name="save" code="app.save" />
		<security:authorize access="hasRole('HACKER')">
			<acme:cancel url="application/hacker/list.do" code="app.cancel" />
		</security:authorize>
		<security:authorize access="hasRole('COMPANY')">
			<acme:cancel url="application/company/list.do" code="app.cancel" />
		</security:authorize>
	</jstl:if>

	<jstl:if test="${isRead == true}">
		<security:authorize access="hasRole('HACKER')">
			<acme:cancel url="application/hacker/list.do" code="app.back" />
		</security:authorize>
		<security:authorize access="hasRole('COMPANY')">
			<acme:cancel url="application/company/list.do" code="app.back" />
		</security:authorize>
	</jstl:if>
</form:form>
