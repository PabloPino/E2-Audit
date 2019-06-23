<%--
 * edit.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authentication property="principal.username" var="username" />
<jstl:set value="${entityExam.application.rookie.userAccount.username}" var="usernameRookie" />
<jstl:set value="${entityExam.application.position.company.userAccount.username}" var="usernameCompany" />

<jstl:choose>
<jstl:when test="${username == usernameRookie or username == usernameCompany}">

	<%

		try {

		EntityExam javaEntityExam = (EntityExam) request.getAttribute("entityExam");

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

		<div class="${cssClass}">	

			<img src="${entityExam.picture}" alt="${entityExam.picture}" width="500px" height="300px" />
			
			<acme:out code="entityexam.ticker" value="${entityExam.ticker}" />
			
			<spring:message code="entityexam.dateformat" var="dateformatmessage" />
			<fmt:formatDate value="${entityExam.publicationMoment}" pattern="${dateformatmessage}" var="publicationMoment" />
			<jstl:if test="${not (publicationMoment == null)}">
				<acme:out code="entityexam.publicationmoment" value="${publicationMoment}" />
			</jstl:if>
			
			<acme:out code="entityexam.body" value="${entityExam.body}" />
			<acme:out code="entityexam.draft" value="${entityExam.draft}" />
			
		</div>	
		
		<security:authorize access="hasRole('COMPANY')">
			<acme:cancel url="entityexam/company/list.do?applicationId=${entityExam.application.id}" code="entityexam.back" />
		</security:authorize>
		<security:authorize access="hasRole('ROOKIE')">
			<acme:cancel url="entityexam/rookie/list.do?applicationId=${entityExam.application.id}" code="entityexam.back" />
		</security:authorize>

	

</jstl:when>
<jstl:otherwise>

	<acme:cancel url="" code="entityexam.back" />

</jstl:otherwise>
</jstl:choose>