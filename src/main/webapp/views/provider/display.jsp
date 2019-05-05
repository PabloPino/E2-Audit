<%--
 * action-1.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<img src="<jstl:out value='${provider.photo}' />"
	alt="<jstl:out value='${provider.photo}' />" />

<p>
	<spring:message code="company.name" />
	:
	<jstl:out value="${provider.name}" />
</p>
<p>
	<spring:message code="company.surname" />
	:
	<jstl:out value="${provider.surname}" />
</p>
<p>
	<spring:message code="company.email" />
	:
	<jstl:out value="${provider.email}" />
</p>
<p>
	<spring:message code="company.phone" />
	:
	<jstl:out value="${provider.phone}" />
</p>
<p>
	<spring:message code="company.address" />
	:
	<jstl:out value="${provider.address}" />
</p>
<p>
	<spring:message code="company.spammer" />
	:
	<jstl:choose>
		<jstl:when test="${provider.spammer}">
			<jstl:out value="${provider.spammer}" />
		</jstl:when>
		<jstl:otherwise>
			<jstl:out value="N/A" />
		</jstl:otherwise>
	</jstl:choose>
</p>
<p>
	<spring:message code="company.banned" />
	:
	<jstl:out value="${provider.banned}" />
</p>
<p>
	<spring:message code="provider.make" />
	:
	<jstl:out value="${provider.make}" />
</p>
<p>
	<spring:message code="company.vatnumber"/>
	:
	<jstl:out value="${provider.VATNumber}" />
</p>


<jstl:if test="${isPrincipalAuthorizedEdit}">
	<a
		href="company/company/edit.do?companyId=${company.id}"><spring:message
			code="company.edit" /></a>


</jstl:if>

	<acme:cancel url="/position/list.do" code="company.backPosition" />
	<acme:cancel url="/company/any/list.do" code="company.backCompanies" />



