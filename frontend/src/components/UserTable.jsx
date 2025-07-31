import React from "react";

const UserTable = ({ users, onView, onEdit, onDelete }) => (
  <table className="table table-striped">
    <thead>
      <tr>
        <th>ID</th>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Email</th>
        <th>Username</th>
        <th>Actions</th>
      </tr>
    </thead>
    <tbody>
      {users.map((user) => (
        <tr key={user.id}>
          <td>{user.id}</td>
          <td>{user.firstName}</td>
          <td>{user.lastName}</td>
          <td>{user.email}</td>
          <td>{user.username}</td>
          <td>
            <button className="btn btn-info btn-sm me-2" onClick={() => onView(user.id)}>View</button>
            <button className="btn btn-warning btn-sm me-2" onClick={() => onEdit(user.id)}>Edit</button>
            <button className="btn btn-danger btn-sm" onClick={() => onDelete(user.id)}>Delete</button>
          </td>
        </tr>
      ))}
    </tbody>
  </table>
);

export default UserTable;
