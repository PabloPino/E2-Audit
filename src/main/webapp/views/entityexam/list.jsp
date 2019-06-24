<%--
 * action-1.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page import="org.springframework.http.HttpRequest"%>
<%@page import="domain.EntityExam"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.concurrent.TimeUnit" %>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authorize access="isAuthenticated()">
	<security:authentication property="principal.username" var="username" />
</security:authorize>
<security:authorize access="!isAuthenticated()">
	<jstl:set value="${null}" var="username" />
</security:authorize>

<jstl:set value="${audit.auditor.userAccount.username}" var="usernameAuditor" />

<jstl:choose>
<jstl:when test="${(audit.finalMode) or (username == usernameAuditor)}">

	<display:table name="entityExams" id="row" requestURI="${requestURI}"
		pagesize="5" class="displaytag">

	<%

		try {

		EntityExam javaEntityExam = (EntityExam) row;

		Long oneMonthMilis = 2592000000L;

		Long now = Calendar.getInstance().getTime().getTime();

		if(javaEntityExam.getPublicationMoment() == null) {

	%>

		<jstl:set var="cssClass" value="" />

		<% } else {

				if(now - javaEntityExam.getPublicationMoment().getTime() < oneMonthMilis) { %>

		<jstl:set var="cssClass" value="color1" />

		<% 		} else if(now - javaEntityExam.getPublicationMoment().getTime() > (2 * oneMonthMilis)) { %>

		<jstl:set var="cssClass" value="color3" />

		<% 		} else { %>

		<jstl:set var="cssClass" value="color2" />

		<% 	}}} catch(NullPointerException nexcp) { %>
		
		<jstl:set var="cssClass" value="" />
		
		<% } %>
	
		<acme:column value="${row.ticker}" code="entityexam.ticker" cssClass="${cssClass}" />
		<acme:column value="${row.body}" code="entityexam.body" cssClass="${cssClass}" />
		<acme:column value="${row.picture}" code="entityexam.picture" alt="${row.picture}" url="true" image="true" width="500px" height="300px" cssClass="${cssClass}" />
		<spring:message code="entityexam.dateformat" var="dateformatmessage" />
		<fmt:formatDate value="${row.publicationMoment}" pattern="${dateformatmessage}" var="publicationMoment" />
		<acme:column value="${publicationMoment}" code="entityexam.publicationmoment" cssClass="${cssClass}" />
		<spring:message code="entityexam.edit" var="altEntityExamEdit" />
		<spring:message code="entityexam.display" var="altEntityExamDisplay" />
		<security:authorize access="hasRole('AUDITOR')">
			<acme:column value="entityexam/auditor/edit.do?entityExamId=${row.id}" url="true" alt="${altEntityExamEdit}" 
				test="${row.audit.auditor.userAccount.username == username and row.draft}" cssClass="${cssClass}" />
			<acme:column value="entityexam/auditor/display.do?entityExamId=${row.id}" url="true" alt="${altEntityExamDisplay}" 
				test="${row.audit.auditor.userAccount.username == username}" cssClass="${cssClass}" />
		</security:authorize>
		
	</display:table>
	
	<jstl:if test="${username == usernameAuditor}">
		<acme:cancel url="entityexam/auditor/create.do" code="entityexam.create" />
	</jstl:if>
	<security:authorize access="hasRole('AUDITOR')">
		<acme:cancel url="audit/list.do" code="entityexam.back" />
	</security:authorize>	
	<security:authorize access="!hasRole('AUDITOR')">
		<acme:cancel url="audit/listAudits.do?positionId=${audit.position.id}" code="entityexam.back" />
	</security:authorize>
	
</jstl:when>
<jstl:otherwise>

	<acme:cancel url="" code="entityexam.back" />

</jstl:otherwise>
</jstl:choose>