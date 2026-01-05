import { ReactNode } from "react"

function CustomCard({children}: {children: ReactNode}) {
  return (
    <div>
      <h1>Pealkiri</h1>
      {children}
    </div>
  )
}

export default CustomCard