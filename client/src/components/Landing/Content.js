import styled from "styled-components"
import img1 from "../../assets/img/land/1.png";
import img2 from "../../assets/img/land/2.png";
import img3 from "../../assets/img/land/3.png";
import { BsChevronDoubleDown, BsChevronDoubleUp } from "react-icons/bs"

const StyledContent = styled.div`
    width: 100%;
    height: 298%;
    transition: transform 0.8s ease;

    > div:nth-of-type(${({located}) => located + 1}) {
        height: 34%;
    }

    > div {
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
    }

    .visible {
        display: flex;
        align-items: center;
    }

    .down {
        font-size: 60pt;
        color: gray;
    }

    @keyframes fadeIn {
        from {
          transform: translateY(100px);
          opacity: 0;
        }
      
        to {
            transform: translateY(0px);
            opacity: 1;
        }
    }

    @keyframes fadeRight {
        from {
          transform: translateX(100px);
          opacity: 0;
        }
      
        to {
            transform: translateX(0px);
            opacity: 1;
        }
    }

    @keyframes fadeLeft {
        from {
          transform: translateX(-100px);
          opacity: 0;
        }
      
        to {
            transform: translateX(0px);
            opacity: 1;
        }
    }

    @keyframes down {
        form {
            transform: translateY(0%);
        }

        to {
            transform: translateY(1000%);
        }
    }

    @media screen and (min-width: 1460px) {
        font-size: 28pt;
        font-weight: 100;

        img {
            height: 100%;
        }
    }

    @media screen and (max-width: 1460px) {
        font-size: 20pt;
        font-weight: 100;

        img {
            height: 60%;
        }
    }

    @media screen and (max-width: 1000px) {
        font-size: 15pt;
        font-weight: 100;

        img {
            height: 40%;
        }
    }
`

const StyledFirst = styled.div`
    width: 100%;
    height: 33%;

    > div {
        display: flex;
        align-items: center;

        div:first-child {
            height: 60%;
            opacity: 0;
        }
    
        div:nth-of-type(2) {
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: flex-start;
    
            span:first-child {
                margin: 0px 0px 15px 0px;
                opacity: 0;
            }
    
            span:last-child {
                margin: 0px;
                opacity: 0;
            }
        }
    }

    .visible {
        animation: fadeIn 1.5s ease-out;
        animation-delay: 0.8s;
        animation-fill-mode: forwards;
    }

    .active {
        span:first-child {
            animation: fadeRight 1s ease-out;
            animation-delay: 1.0s;
            animation-fill-mode: forwards;
        }

        span:last-child {
            animation: fadeRight 1s ease-out;
            animation-delay: 1.4s;
            animation-fill-mode: forwards;
        }
    }
`

const StyledSecond = styled.div`
    width: 100%;
    height: 33%;
    padding: 0px 0px 85px 0px;

    > div {
        display: flex;
        align-items: center;

        div:nth-of-type(2) {
            height: 50%;
            opacity: 0;
            margin : 0px 0px 0px 80px;
        }

        div:first-child {
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: flex-start;

            span:first-child {
                margin: 0px 0px 15px 0px;
                opacity: 0;
            }

            span:last-child {
                margin: 0px;
                opacity: 0;
            }
        }
    }

    .visible {
        animation: fadeIn 1.5s ease-out;
        animation-delay: 0.8s;
        animation-fill-mode: forwards;
    }

    .active {
        span:first-child {
            animation: fadeLeft 1s ease-out;
            animation-delay: 1.0s;
            animation-fill-mode: forwards;
        }

        span:last-child {
            animation: fadeLeft 1s ease-out;
            animation-delay: 1.4s;
            animation-fill-mode: forwards;
        }
    }
`

const StyledThird = styled.div`
    width: 100%;
    height: 33%;
    padding: 0px 0px 85px 0px;

    > div {
        display: flex;
        align-items: center;

        div:first-child {
            height: 50%;
            opacity: 0;
        }

        div:nth-of-type(2) {
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: flex-start;

            span:first-child {
                margin: 0px 0px 15px 0px;
                opacity: 0;
            }

            span:last-child {
                margin: 0px;
                opacity: 0;
            }
        }
    }

    .visible {
        animation: fadeIn 1.5s ease-out;
        animation-delay: 0.8s;
        animation-fill-mode: forwards;
    }

    .active {
        span:first-child {
            animation: fadeRight 1s ease-out;
            animation-delay: 1.0s;
            animation-fill-mode: forwards;
        }

        span:last-child {
            animation: fadeRight 1s ease-out;
            animation-delay: 1.4s;
            animation-fill-mode: forwards;
        }
    }
`

export const Content = ({located}) => {
    return (
        <StyledContent located={located}>
            <StyledFirst located={located}>
                {
                    located === 0 && (
                        <div>
                            <div className={located === 0 ? "visible" : null}>
                                <img src={img3} alt="img" />
                            </div>
                            <div className={located === 0 ? "active" : null}>
                                <span>독창적인 콘텐츠로,</span>
                                <span>당신의 반려식물을 자랑해보세요!</span>
                            </div>
                        </div>
                    )
                }
                {
                    located === 0 && (
                        <div className="down">
                            <BsChevronDoubleUp />
                        </div>
                    )
                }
            </StyledFirst>
            <StyledSecond located={located} className={located === 0 ? "located" : null}>
                {
                    located === 1 && (
                        <div className="down">
                            <BsChevronDoubleDown />
                        </div>
                    )
                }
                {
                    located === 1 && (
                        <div>   
                            <div className={located === 1 ? "active" : "none"}>
                                <span>오직 하나뿐인 당신만의 갤러리,</span>
                                <span>나만의 갤러리를 꾸며보세요!</span>
                            </div>
                            <div className={located === 1 ? "visible" : null}>
                                <img src={img1} alt="img" />
                            </div>
                        </div>
                    )
                }
                {
                    located === 1 && (
                        <div className="down">
                            <BsChevronDoubleUp />
                        </div>
                    )
                }
            </StyledSecond>
            <StyledThird located={located}>
                {
                    located === 2 && (
                        <div className="down">
                            <BsChevronDoubleDown />
                        </div>
                    )
                }
                {
                    located === 2 && (
                        <div>
                            <div className={located === 2 ? "visible" : null}>
                                <img src={img2} alt="img" />
                            </div>
                            <div className={located === 2 ? "active" : null}>
                                <span>독창적인 콘텐츠를 만들고,</span>
                                <span>다양한 사용자와 상호작용해보세요!</span>
                            </div>
                        </div>
                    )
                }
            </StyledThird>
        </StyledContent>
    )
}