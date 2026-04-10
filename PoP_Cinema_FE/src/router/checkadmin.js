import axios from "axios";
import { createToaster } from "@meforma/vue-toaster";
const toaster = createToaster({ position: "top-right" });
export default function (to, from, next) {
   const token = localStorage.getItem("key_admin");
    
    if (!token) {
        toaster.error("Vui lòng đăng nhập trước khi tiếp tục");
        next("/admin/dang-nhap");
    } else {
        axios
            .get("http://localhost:8080/api/admin/check-token", {
                headers: {
                    Authorization: `Bearer ${token}`
                },
            })
            .then((res) => {
                if (res.data.status) {
                    localStorage.setItem("ho_va_ten", res.data.ho_ten);
                    next();
                } else {
                   
                    toaster.error(res.data.message);
                    next("/admin/dang-nhap");
                }
            })
           
    }
}