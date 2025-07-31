import React from "react";
import { useNavigate } from "react-router-dom";

const Welcome = () => {
  const navigate = useNavigate();

  return (
    <div style={{
      minHeight: "100vh",
      display: "flex",
      flexDirection: "column",
      justifyContent: "center",
      alignItems: "center",
      background: "linear-gradient(135deg, #f8fafc 0%, #e0e7ef 100%)"
    }}>
      <div style={{
        background: "#fff",
        borderRadius: 16,
        boxShadow: "0 4px 24px rgba(0,0,0,0.08)",
        padding: "48px 32px",
        maxWidth: 400,
        width: "100%",
        textAlign: "center"
      }}>
        <h1 style={{ fontWeight: 700, marginBottom: 16, color: "#2d3748" }}>Welcome to User Management Portal</h1>
        <p style={{ color: "#4a5568", marginBottom: 32 }}>
          Manage your users efficiently and securely. Please login or register to continue.
        </p>
        <div style={{ display: "flex", gap: 16, justifyContent: "center" }}>
          <button
            onClick={() => navigate("/login")}
            style={{
              padding: "10px 28px",
              borderRadius: 8,
              border: "none",
              background: "#2563eb",
              color: "#fff",
              fontWeight: 600,
              fontSize: 16,
              cursor: "pointer",
              transition: "background 0.2s"
            }}
          >
            Login
          </button>
          <button
            onClick={() => navigate("/register")}
            style={{
              padding: "10px 28px",
              borderRadius: 8,
              border: "1px solid #2563eb",
              background: "#fff",
              color: "#2563eb",
              fontWeight: 600,
              fontSize: 16,
              cursor: "pointer",
              transition: "background 0.2s"
            }}
          >
            Register
          </button>
        </div>
      </div>
    </div>
  );
};

export default Welcome; 