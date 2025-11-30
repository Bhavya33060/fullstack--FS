export const getBackendURL = () => {
  const port = window.location.port;

  // ⭐ Kubernetes NodePort frontend runs on 32000 → backend = 32001
  if (port === "32000") {
    return "http://localhost:32001";
  }

  // ⭐ Docker frontend runs on 3000 → backend = 8084
  if (port === "3000") {
    return "http://localhost:8084";
  }

  // ⭐ Dev mode (vite) → use .env
  return import.meta.env.VITE_BACKEND_URL || "http://localhost:8084";
};

export const API_BASE = getBackendURL();
