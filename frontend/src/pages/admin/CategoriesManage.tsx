import { useEffect, useState } from "react";
import useFetch from "../../hooks/useFetch";
import { Category } from "../../models/Category";

const backendUrl = import.meta.env.VITE_API_HOST;

function CategoriesManage() {
  const [categories, setCategories] = useState<Category[]>([]);
  const dbCategories = useFetch<Category>({endpoint: "categories"});

  useEffect(() => {
    setCategories(dbCategories);
  }, [dbCategories]);

  const [category, setCategory] = useState({"name": ""});

  function addCategory() {
    fetch(backendUrl + "/categories", {
      method: "POST",
      body: JSON.stringify(category),
      headers: {
        "Content-Type": "application/json",
        "Authorization": "Bearer " + sessionStorage.getItem("token")
      }
    })
      .then(res => res.json())
      .then(json => setCategories(json))
  }

  return (
    <div className='container'>
        <h2 className='text-center'>List of Categories</h2>
        {/* TODO: label+input+button */}
        <label>Category</label> <br />
        <input onChange={(e) => setCategory({"name": e.target.value})} type="text" /> <br />
        <button onClick={() => addCategory()}>Add Category</button>
        <table className='table table-striped table-bordered'>
          <thead className='table-success'>
            <tr>
              <th>ID</th>
              <th>Name</th>
            </tr>
          </thead>
            <tbody>
              {
                categories.map(category =>
                  <tr key={category.id}>
                    <td>{category.id}</td>
                    <td>{category.name}</td>
                  </tr>)
              }
            </tbody>
        </table>
    </div>
  )
}

export default CategoriesManage