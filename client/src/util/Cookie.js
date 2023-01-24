import { Cookies } from "react-cookie";

class Cookie {
    constructor() {
      this.cookies = new Cookies();
    }
  
    set(name, value, option) {
      this.cookies.set(name, value, option);

      // 해당 메소드 사용시 가능한 option
      // path:[/], expires:[ms단위], secure:[true, false], httpOnly:[true, false]
      // 이때 httpOnly는 반드시 true로 한다.
    }
  
    get(name) {
      return this.cookies.get(name);
    }

    remove(name) {
      this.cookies.remove(name);
    }
  }
  
  export default Cookie;