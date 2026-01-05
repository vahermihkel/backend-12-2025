import { Link } from "react-router-dom"

function AdminHome() {
  return (
    <div>
      <Link to="/admin/manage-products">
        <button>Halda tooteid</button>
      </Link>
    </div>
  )
}

export default AdminHome