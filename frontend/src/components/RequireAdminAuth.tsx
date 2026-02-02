import { useContext } from "react";
import { AuthContext } from "../context/AuthContext";
import { Navigate, Outlet } from "react-router-dom";
// import NotFound from "../pages/NotFound";

function RequireAuth() {
  const {person} = useContext(AuthContext);

  if (person.role === "ADMIN" || person.role === "SUPERADMIN") {
    return <Navigate to="/login" replace />
    // return <NotFound />
  }

  return (
    <Outlet />
  )
}

export default RequireAuth