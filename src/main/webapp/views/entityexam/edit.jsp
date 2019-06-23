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

<security:authentication property="principal.username" var="username" />

<security:authorize access="hasRole('ROOKIE')">

	<jstl:if test="${(entityExam.id == 0 or (entityExam.application.rookie.userAccount.username == username)) and isDraft}">
		
		<form:form action="entityexam/rookie/edit.do" modelAttribute="entityExam">
		
			<form:hidden path="id" />
			<form:hidden path="version" />
			
			<acme:textarea path="body" code="entityexam.body" />
			<acme:textbox path="picture" code="entityexam.picture" />
			
			<acme:select path="application" code="entityexam.application" items="${applications}" itemLabel="position.title" />
			
			<jstl:if test="${entityExam.id > 0}">
				<acme:checkbox path="draft" code="entityexam.draft" />
			</jstl:if>
			
			<acme:form-buttons testDelete="${entityExam.id > 0}" />
		
		</form:form>
		
		<jstl:choose>
			<jstl:when test="${entityExam.application.id > 0}">
				<acme:cancel url="entityexam/rookie/list.do?applicationId=${entityExam.application.id}" code="entityexam.back" />
			</jstl:when>
			<jstl:otherwise>
				<acme:cancel url="application/rookie/list.do" code="entityexam.back" />
			</jstl:otherwise>
		</jstl:choose>

	</jstl:if>
	
	<jstl:if test="${not (entityExam.id == 0 or (entityExam.application.rookie.userAccount.username == username)) or not isDraft}">
	
		<acme:cancel url="" code="entityexam.back" />
	
	</jstl:if>
	
</security:authorize>

<security:authorize access="!hasRole('ROOKIE')">

	<acme:cancel url="" code="entityexam.back" />

</security:authorize>



