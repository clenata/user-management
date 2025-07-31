import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../services/api";

const Register = () => {
  const [form, setForm] = useState({
    firstName: "",
    lastName: "",
    email: "",
    username: "",
    password: "",
  });
  const [success, setSuccess] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleChange = (e) => {
    // Prevent autofill from copying email into username
    if (e.target.name === "username" && e.target.value === form.email) {
      setError("Username cannot be the same as email. Please choose a unique username.");
      return;
    }
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  // Basic client-side validation
  const validateForm = () => {
    if (
      !form.firstName.trim() ||
      !form.lastName.trim() ||
      !form.email.trim() ||
      !form.username.trim() ||
      !form.password.trim()
    ) {
      setError("All fields are required.");
      return false;
    }
    if (form.username === form.email) {
      setError("Username cannot be the same as email. Please choose a unique username.");
      return false;
    }
    // Simple email regex
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(form.email)) {
      setError("Please enter a valid email address.");
      return false;
    }
    if (form.password.length < 6) {
      setError("Password must be at least 6 characters.");
      return false;
    }
    return true;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setSuccess("");
    setError("");
    if (!validateForm()) return;
    
    console.log("Attempting registration with data:", form);
    
    try {
      const res = await api.post("/users/register", form);
      console.log("Registration successful:", res.data);
      setSuccess("Registration successful! Redirecting to dashboard...");
      localStorage.setItem("loggedIn", "true");
      localStorage.setItem("username", form.username);
      setTimeout(() => navigate("/dashboard"), 1000);
    } catch (err) {
      console.error("Registration error:", err); 
      console.error("Error response:", err.response);
      
      let backendMsg = "Registration failed. Please try again.";
      if (err.response && err.response.data) {
        if (typeof err.response.data === "string") {
          backendMsg = err.response.data;
        } else if (err.response.data.message) {
          backendMsg = err.response.data.message;
        } else {
          backendMsg = JSON.stringify(err.response.data);
        }
      } else if (err.message) {
        backendMsg = `Network error: ${err.message}`;
      }
      
      console.log("Setting error message:", backendMsg);
      setError(backendMsg);
    }
  };

  return (
    <div className="container mt-5" style={{ maxWidth: 500 }}>
      <h2>Register</h2>
      {success && <div className="alert alert-success">{success}</div>}
      {error && <div className="alert alert-danger">{error}</div>}
      <form onSubmit={handleSubmit} autoComplete="off">
        <div className="mb-3">
          <label className="form-label">First Name</label>
          <input
            type="text"
            className="form-control"
            name="firstName"
            value={form.firstName}
            onChange={handleChange}
            required
            autoComplete="given-name"
          />
        </div>
        <div className="mb-3">
          <label className="form-label">Last Name</label>
          <input
            type="text"
            className="form-control"
            name="lastName"
            value={form.lastName}
            onChange={handleChange}
            required
            autoComplete="family-name"
          />
        </div>
        <div className="mb-3">
          <label className="form-label">Email</label>
          <input
            type="email"
            className="form-control"
            name="email"
            value={form.email}
            onChange={handleChange}
            required
            autoComplete="email"
          />
        </div>
        <div className="mb-3">
          <label className="form-label">Username</label>
          <input
            type="text"
            className="form-control"
            name="username"
            value={form.username}
            onChange={handleChange}
            required
            autoComplete="username"
          />
        </div>
        <div className="mb-3">
          <label className="form-label">Password</label>
          <input
            type="password"
            className="form-control"
            name="password"
            value={form.password}
            onChange={handleChange}
            required
            minLength={6}
            autoComplete="new-password"
          />
        </div>
        <button type="submit" className="btn btn-primary w-100">
          Register
        </button>
      </form>
    </div>
  );
};

export default Register;
