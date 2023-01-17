import { Cookies } from "react-cookie";

class Cookie {
    constructor() {
      this.cookies = new Cookies();
    }
  
    set(name, value, option) {
      this.cookies.set(name, value, option);
      this.listUp(name);

      // 해당 메소드 사용시 가능한 option
      // path:[/], expires:[ms단위], secure:[true, false], httpOnly:[true, false]
      // 이때 httpOnly는 반드시 true로 한다.
    }
  
    get(name) {
      return this.cookies.get(name);
    }
  
    listUp(name) {
      if (this.cookies.get("list") === undefined) {
        this.cookies.set("list", name);
      } else {
        const prev = this.cookies.get("list");
        if(prev.includes(name)) return;
        this.cookies.set("list", `${prev} ${name}`);
      }
    }
  
    listDown(name) {
      this.cookies.set(
        "list",
        this.cookies
          .get("list")
          .split(" ")
          .filter((el) => el !== name)
          .join(" ")
      );
      if (this.cookies.get("list").length === 0) {
        this.cookies.remove("list");
      }
    }
  
    remove(name) {
      this.cookies.remove(name);
      this.listDown(name);
    }
  
    removeAll() {
      this.cookies
        .get("list")
        .split(" ")
        .forEach((el) => {
          this.remove(el);
        });
    }
  
    status() {
      return this.cookies.get("list").split(" ");
    }
  }
  
  export default Cookie;