/*Work with view's DOM-tree*/
showAllUsers();
showUser();


/*Main admin window, all users*/
async function showAllUsers() {
    let admin=await fetch("http://localhost:8080/api/admin").json()
    document.getElementById('tableOfUsers').innerHTML = ``;
    admin.forEach(user => showEachUser(user));
}

async function showEachUser (user) {
    let mainAdminPanel = `
        <td >${user.id}</td>
        <td >${user.firstName}</td>
        <td >${user.lastName}</td>
        <td >${user.age}</td>
        <td >${user.username}</td>
        <td>
            <button type="button" class="btn btn-info text-white" data-bs-toggle="modal" data-bs-target="#editModal" data-bs-userName="${user.username}">
                Edit
            </button>
        </td>
        <td>
            <button type="button" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#deleteModal" data-bs-userName="${user.username}">
                Delete
            </button>
        </td>`;
    for (role of user.roles) {
        const roleName = role.name;
        mainAdminPanel += `<td><span>${roleName}</span></td>`;
    }

    /*JavaScript function that lets you grab an HTML element, by its id , and perform an action on it*/
    document.getElementById('tableOfUsers').innerHTML += mainAdminPanel;
}

/*Main user window*/
async function  showUser() {
    let user = await (await fetch("http://localhost:8080/api/user")).json();

    let userPanel = `
        <td>${user.id}</td>
        <td>${user.firstName}</td>
        <td>${user.lastName}</td>
        <td>${user.age}</td>
        <td>${user.username}</td>
        `;
    for (role of user.roles) {
        const roleName = role.name;
        userPanel += `<td> <span>${roleName}</span> </td>`;
    }
    document.getElementById('showUser').innerHTML = userPanel;
}

/*Delete User Window*/
const showDeleteWindow = (user) => {
    let html = `
        <label class="d-block mx-auto pt-1 mt-2 mb-0 text-center fs-5 fw-bold">ID</label>
        <input  value="${user.id}" disabled type="text" class="form-control mx-auto" style="width: 300px;">
        <label class="form-label d-block mx-auto pt-1 mt-3 mb-0 text-center fs-5 fw-bold">First name</label>
        <input value="${user.firstName}" disabled type="text" class="form-control mx-auto" style="width: 300px;">
        <label class="form-label d-block mx-auto pt-1 mt-3 mb-0 text-center fs-5 fw-bold">Last name</label>
        <input value="${user.lastName}" disabled type="text" class="form-control mx-auto" style="width: 300px;">
        <label class="form-label d-block mx-auto pt-1 mt-3 mb-0 text-center fs-5 fw-bold">Age</label>
        <input value="${user.age}" disabled type="number" class="form-control mx-auto" style="width: 300px;">
        <label class="form-label d-block mx-auto pt-1 mt-3 mb-0 text-center fs-5 fw-bold">Email</label>
        <input id="deleteUserName" value="${user.username}" disabled type="text" class="form-control mx-auto" style="width: 300px;">
        <label class="form-label d-block mx-auto pt-1 mt-3 mb-0 text-center fs-5 fw-bold">Role</label>
        <select size="2" disabled class="form-select mx-auto" style="width: 300px;">
    `;

    /* html+=
    user.roles.map((role) => `<option label="${role}"></option>`).join("")
    */

    for (role of user.roles) {
        const roleName = role.name;
        html += `
            <option label="${roleName}"></option>
        `;
    }
    html += `</select>`;
    document.getElementById('deleteUserInfo').innerHTML = html;
};

document.getElementById('deleteModal').addEventListener('show.bs.modal',  async (event) => {
    const userName = event.relatedTarget.getAttribute('data-bs-userName');
    const response = await fetch("http://localhost:8080/api/admin/" + userName).then(response => response.json())
    showDeleteWindow(response);
});

document.getElementById('deleteForm').addEventListener('submit', async (event) => {
    const userName = document.getElementById('deleteUserName').getAttribute('value');
    event.preventDefault();
    const options = {
        method: 'DELETE',
        body: JSON.stringify(),
        headers: {
            'Content-Type': 'application/json'
        }
    };
    await fetch("http://localhost:8080/api/admin/" + userName, options)
    showAllUsers();
});



/*Edit User Window*/
//принимает обновленные данные юзера
//помещает в респонс, преобразует к JSON
//асинхронно переправляет на сторону back-end
const renderEditModalContent = (user) => {
    let html = `
        <label class="d-block mx-auto pt-1 mt-2 mb-0 text-center fs-5 fw-bold">ID</label>
        <input id="editUserId" value="${user.id}" disabled type="text" class="form-control mx-auto" style="width: 300px;">
        <label class="form-label d-block mx-auto pt-1 mt-3 mb-0 text-center fs-5 fw-bold">First name</label>
        <input id="editUserFirstName" value="${user.firstName}" type="text" class="form-control mx-auto" style="width: 300px;">
        <label class="form-label d-block mx-auto pt-1 mt-3 mb-0 text-center fs-5 fw-bold">Last name</label>
        <input id="editUserLastName"value="${user.lastName}" type="text" class="form-control mx-auto" style="width: 300px;">
        <label class="form-label d-block mx-auto pt-1 mt-3 mb-0 text-center fs-5 fw-bold">Age</label>
        <input id="editUserAge" value="${user.age}" type="number" class="form-control mx-auto" style="width: 300px;">
        <label class="form-label d-block mx-auto pt-1 mt-3 mb-0 text-center fs-5 fw-bold">Email</label>
        <input id="editUserName" value="${user.username}" type="text" class="form-control mx-auto" style="width: 300px;">
        <label class="form-label d-block mx-auto pt-1 mt-3 mb-0 text-center fs-5 fw-bold">Password</label>
        <input id="editUserPassword" value="" type="text" class="form-control mx-auto" style="width: 300px;">
        <label class="form-label d-block mx-auto pt-1 mt-3 mb-0 text-center fs-5 fw-bold">Role</label>
        <select size="2" multiple class="form-select mx-auto" style="width: 300px;">
        <option id="ADMIN" label="ADMIN"></option>
        <option id="USER" label="USER"></option>
        </select>
    `;
    document.getElementById('editModalContent').innerHTML = html;
};

document.getElementById('editModal').addEventListener('show.bs.modal', async (event) => {
    const userName = event.relatedTarget.getAttribute('data-bs-userName');
    const response = await fetch("http://localhost:8080/api/admin/" + userName).then(response => response.json())
    renderEditModalContent(response);
});

/*Логика редактирования юзера*/
document.getElementById('editForm').addEventListener('submit', async (event) => {
    event.preventDefault();
    const userRolesEdited = [];
    if (document.getElementById('USER').selected) userRolesEdited.push({id: 1, name: 'USER'});
    if (document.getElementById('ADMIN').selected) userRolesEdited.push({id: 2, name: 'ADMIN'});
    const userEdited = {
        id : document.getElementById('editUserId').value,
        firstName: document.getElementById('editUserFirstName').value,
        lastName: document.getElementById('editUserLastName').value,
        age: document.getElementById('editUserAge').value,
        username: document.getElementById('editUserName').value,
        password: document.getElementById('editUserPassword').value,
        roles: userRolesEdited
    };
    const options = {
        method: 'PUT',
        body: JSON.stringify(userEdited),
        headers: {
            'Content-Type': 'application/json'
        }
    };
    //улетает на контроллер updateUser
    await fetch("http://localhost:8080/api/admin/", options)
    showAllUsers();
});


/*Логика добавления нового юзера*/
document.getElementById('createUserForm').addEventListener('submit', async (event) => {
    event.preventDefault();
    const userRolesEdited = [];
    if (document.getElementById('newRoleUser').selected) userRolesEdited.push({id: 1, name: 'USER'});
    if (document.getElementById('newRoleAdmin').selected) userRolesEdited.push({id: 2, name: 'ADMIN'});
    const userEdited = {
        id : 100,
        firstName: document.getElementById('newUserFirstName').value,
        lastName: document.getElementById('newUserLastName').value,
        age: document.getElementById('newUserAge').value,
        username: document.getElementById('newUserName').value,
        password: document.getElementById('newUserPassword').value,
        roles: userRolesEdited
    };
    const options = {
        method: 'POST',
        body: JSON.stringify(userEdited),
        headers: {
            'Content-Type': 'application/json'
        }
    };
    //улетает на контроллер addNewUser
    await fetch("http://localhost:8080/api/admin/", options)
    showAllUsers();
    bootstrap.Tab.getInstance(document.querySelector('#nav-tab a[href="#nav-usersTable"]')).show();
});


