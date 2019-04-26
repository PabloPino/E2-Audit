<%--
 * action-1.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:hidden path="id" />
<form:hidden path="version" />
<form:hidden path="userAccount" />

<acme:out code="hacker.name" value="${hacker.name}"/>
<acme:out code="hacker.middleName" value="${hacker.middleName}"/>
<acme:out code="hacker.surname" value="${hacker.surname}"/>
<acme:out code="hacker.photo" value="${hacker.photo}"/>
<acme:out code="hacker.email" value="${hacker.email}"/>

<acme:out code="hacker.phone" value="${hacker.phone}"/>
<acme:out code="hacker.address" value="${hacker.address}"/>

<security:authorize access="hasRole('ADMIN')">
<jstl:choose>
	<jstl:when test="${hacker.spammer}">
		<jstl:out value="${hacker.spammer}" />
	</jstl:when>
	<jstl:otherwise>
		<jstl:out value="N/A" />
	</jstl:otherwise>
</jstl:choose>
<acme:out code="hacker.score" value="${hacker.score}"/>
<acme:out code="hacker.vatnumber" value="${hacker.VATNumber}" />
</security:authorize>




<security:authentication property="principal.username" var="username" />
	<jstl:if
		test='${customer.userAccount.username == username || customer.id == 0}'>
		
<input type="button" name="edit" value="<spring:message code="hacker.edit"></spring:message>" onclick="javascript:relativeRedir('hacker/brotherhood/edit.do?hackerId=${hacker.id}')"/>	
	</jstl:if>

<input type="button" name="cancel" value="<spring:message code="hacker.cancel"></spring:message>" onclick="javascript:relativeRedir('hacker/brotherhood/list.do')" />	










