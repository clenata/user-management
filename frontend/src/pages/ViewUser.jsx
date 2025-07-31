import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import api from "../services/api";

const ViewUser = () => {
  const { id } = useParams();
  const [user, setUser] = useState(null);
  const [error, setError] = useState("");

  useEffect(() => {
    const fetchUser = async () => {
      try {
        const res = await api.get(`/users/${id}`);
        setUser(res.data);
      } catch {
        setError("Failed to load user");
      }
    };
    fetchUser();
  }, [id]);

  if (error) return <div className="container mt-5"><div className="alert alert-danger">{error}</div></div>;
  if (!user) return <div className="container mt-5">Loading...</div>;

  return (
    <div className="container mt-5" style={{ maxWidth: 500 }}>
      <h2>User Details</h2>
      <ul className="list-group">
        <li className="list-group-item"><strong>ID:</strong> {user.id}</li>
        <li className="list-group-item"><strong>First Name:</strong> {user.firstName}</li>
        <li className="list-group-item"><strong>Last Name:</strong> {user.lastName}</li>
        <li className="list-group-item"><strong>Email:</strong> {user.email}</li>
        <li className="list-group-item"><strong>Username:</strong> {user.username}</li>
      </ul>
    </div>
  );
};

export default ViewUser;
