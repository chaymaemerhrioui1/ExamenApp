<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<div class="collapse navbar-collapse" id="navbarNav">
	<ul class="navbar-nav">
		<li class="nav-item"><a class="nav-link active"
			aria-current="page"
			href="${pageContext.request.contextPath}/admin/showAdminHome">Home</a></li>

		<li class="nav-item dropdown"><a class="nav-link dropdown-toggle"
			href="#" id="navbarScrollingDropdown" Type="button"
			data-bs-toggle="dropdown" aria-expanded="false"> Person
			Management </a>
			<ul class="dropdown-menu" aria-labelledby="navbarScrollingDropdown">
				<li class="dropdown-item"><a class="nav-link"
					href="${pageContext.request.contextPath}/admin/showForm?typePerson=1">Add
						Prof</a></li>
						<li class="dropdown-item"><a class="nav-link"
                        					href="${pageContext.request.contextPath}/admin/showForm?typePerson=3">Add
                        						Cadre Admin</a></li>

				<li class="dropdown-item"><a class="nav-link"
					href="${pageContext.request.contextPath}/admin/managePersons">Manage
						Profs </a></li>
			</ul></li>


			<li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarScrollingDropdown" Type="button" data-bs-toggle="dropdown" aria-expanded="false">
                    Group Management
                </a>
                <ul class="dropdown-menu" aria-labelledby="navbarScrollingDropdown">
                    <li class="dropdown-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/admin/createGroup">Create Group</a>
                    </li>
                    <li class="dropdown-item">
                                        <a class="nav-link" href="${pageContext.request.contextPath}/admin/listGroups">List Groups</a>
                                    </li>
                </ul>
            </li>
            <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="navbarScrollingDropdown" Type="button" data-bs-toggle="dropdown" aria-expanded="false">
                                Element Management
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="navbarScrollingDropdown">
                                <li class="dropdown-item">
                                    <a class="nav-link" href="${pageContext.request.contextPath}/elements-pedagogiques">Manage Element</a>
                                </li>
                            </ul>

                        </li>

                     <li class="nav-item dropdown">
                         <a class="nav-link dropdown-toggle" href="#" id="examManagementDropdown" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                             Exam Management
                         </a>
                         <ul class="dropdown-menu" aria-labelledby="examManagementDropdown">
                             <li class="dropdown-item">
                                 <a class="nav-link" href="${pageContext.request.contextPath}/examens">Plan Exams</a>
                             </li>
                         </ul>
                     </li>








		<li class="nav-item"><form
				action="${pageContext.request.contextPath}/admin/serachPerson"
				class="d-flex" method="GET">
				<input name="cin" class="form-control me-2" type="search"
					placeholder="Saisir CIN" aria-label="Search">
				<button class="btn btn-outline-success" type="submit">Search</button>
			</form></li>

		<li class="nav-item"><f:form
				action="${pageContext.request.contextPath}/logout" method="POST">
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}">

				<button type="submit" class="btn btn-link">logout</button>

			</f:form></li>

	</ul>



</div>


