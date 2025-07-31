import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../services/api";

const Dashboard = () => {
  const [users, setUsers] = useState([]);
  const [currentUser, setCurrentUser] = useState(null);
  const [error, setError] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    // Route protection: redirect if not logged in
    if (localStorage.getItem("loggedIn") !== "true") {
      navigate("/login");
      return;
    }
    const fetchUsers = async () => {
      try {
        const res = await api.get("/users");
        setUsers(res.data.filter((u) => !u.deleted));
      } catch (err) {
        // Do not set error for failed fetch
      }
    };
    fetchUsers();
    // Set current user from localStorage or leave as null
    const username = localStorage.getItem("username");
    if (username) {
      setCurrentUser({ username });
    } else {
      setCurrentUser(null);
    }
  }, [navigate]);

  const handleView = (id) => navigate(`/user/${id}`);
  const handleEdit = (id) => navigate(`/edit/${id}`);
  const handleDelete = async (id) => {
    try {
      await api.delete(`/users/${id}`);
      setUsers(users.filter((u) => u.id !== id));
    } catch {
      setError("Failed to delete user");
    }
  };
  const handleLogout = () => {
    localStorage.removeItem("loggedIn");
    localStorage.removeItem("username");
    navigate("/");
  };

  return (
    <div className="container-fluid min-vh-100 d-flex flex-column justify-content-center align-items-center" style={{ background: "#f8f9fa" }}>
      <div className="w-100" style={{ maxWidth: 1200 }}>
        <div className="d-flex justify-content-between align-items-center mb-4 mt-4">
          <h2 className="fw-bold">User Dashboard</h2>
          <button className="btn btn-secondary" onClick={handleLogout}>Logout</button>
        </div>
        {currentUser && (
          <></>
        )}
        {error && <div className="alert alert-danger">{error}</div>}
        <div className="card shadow-sm">
          <div className="card-body">
            <div className="table-responsive">
              <table className="table table-striped align-middle">
                <thead>
                  <tr>
                    <th scope="col">#</th>
                    <th scope="col">First Name</th>
                    <th scope="col">Last Name</th>
                    <th scope="col">Username</th>
                    <th scope="col">Email</th>
                    <th scope="col" style={{ minWidth: 180 }}>Actions</th>
                  </tr>
                </thead>
                <tbody>
                  {users.map((user, idx) => (
                    <tr key={user.id}>
                      <th scope="row">{idx + 1}</th>
                      <td>{user.firstName}</td>
                      <td>{user.lastName}</td>
                      <td>{user.username}</td>
                      <td>{user.email}</td>
                      <td>
                        <button
                          className="btn btn-sm btn-info me-2"
                          onClick={() => handleView(user.id)}
                        >
                          View
                        </button>
                        <button
                          className="btn btn-sm btn-warning me-2"
                          onClick={() => handleEdit(user.id)}
                        >
                          Edit
                        </button>
                        <button
                          className="btn btn-sm btn-danger"
                          onClick={() => handleDelete(user.id)}
                        >
                          Delete
                        </button>
                      </td>
                    </tr>
                  ))}
                  {users.length === 0 && (
                    <tr>
                      <td colSpan={6} className="text-center">
                        No users found.
                      </td>
                    </tr>
                  )}
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Dashboard;
