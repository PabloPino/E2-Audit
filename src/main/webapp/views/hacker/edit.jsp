<%--
 * action-2.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" uri="/WEB-INF/tags" %>

<jstl:if test="${isPrincipalAuthorizedEdit}">
	<jstl:choose>
	<jstl:when test="${isBrotherhood}">
		<form:form action="hacker/edit.do" method="post" id="formEdit"
			name="formEdit" modelAttribute="hackerForm">
			
			<form:hidden path="id" />
			<form:hidden path="version" />
			
			<acme:userAccount code="hacker.userAccount" />
			<acme:userAccount code="hacker.password" />
			
			<acme:textbox path="name" code="hacker.name" />
			<acme:textbox path="middleName" code="hacker.middleName" />
			<acme:textbox path="surname" code="hacker.surname" />
			<acme:textbox path="photo" code="hacker.photo" />
			<acme:textbox path="email" code="hacker.email" />
			<acme:textbox path="phone" code="hacker.phone" />
			<acme:textbox path="VATNumber" code="hacker.vatnumber" />
			
			
		</form:form>
	</jstl:when>
	<jstl:when test="${isAdmin}">
	
	</jstl:when>
	</jstl:choose>
</jstl:if>