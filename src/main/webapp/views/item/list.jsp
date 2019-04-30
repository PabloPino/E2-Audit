<%--
 * action-1.jsp
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
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>




<display:table name="items" id="row" requestURI="${requestURI}"
	pagesize="10" class="displaytag">

	<security:authorize access="hasRole('PROVIDER')">
		<security:authentication property="principal.username" var="username" />

		<jstl:if test='${row.provider.userAccount.username == username}'>
			<display:column>
				<a href="item/provider/edit.do?itemId=${row.id}"><spring:message
						code="item.edit"></spring:message></a>
			</display:column>
		</jstl:if>

	</security:authorize>

	<display:column titleKey="item.provider">

	</display:column>

	<acme:column code="item.name" value="${row.name}"></acme:column>
	<acme:column code="item.description" value="${row.description}"></acme:column>
	<acme:column code="item.link" value="${row.link}"></acme:column>
	<acme:column code="item.pictures" value="${row.pictures}"></acme:column>


</display:table>


<security:authorize access="hasRole('PROVIDER')">

	<input type="button" name="create"
		value="<spring:message code="item.create"></spring:message>"
		onclick="javascript:relativeRedir('item/provider/create.do')" />

</security:authorize>