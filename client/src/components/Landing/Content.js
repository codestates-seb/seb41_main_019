import styled from "styled-components"

const StyledContent = styled.div`
    width: 100%;
    height: 298%;
    transition: transform 1s ease;

    > div:nth-of-type(${({located}) => located + 1}) {
        height: 34%;
    }

    @keyframes fadeIn {
        from {
          transform: translateY(100px);
          opacity: 0;
        }
      
        to {
            transform: translateY(-20px);
            opacity: 1;
        }
    }
`

const StyledFirst = styled.div`
    width: 100%;
    height: 33%;

    div:last-child {
        height: 100%;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        margin: 0px 0px 0px 30px;

        span:first-child {
            font-size: 40px;
            font-weight: 600;
            margin: 0px 0px 15px 0px;
            opacity: ${({located}) => located === 0 ? "0" : "1"};
        }

        span:last-child {
            font-size: 40px;
            font-weight: 600;
            margin: 0px;
            opacity: ${({located}) => located === 0 ? "0" : "1"};
        }
    }

    .active {
        span:first-child {
            transform: translateY(100px);
            animation: fadeIn 3s ease;
            animation-delay: 0.7s;
            animation-fill-mode: forwards;
        }

        span:last-child {
            transform: translateY(100px);
            animation: fadeIn 3s ease;
            animation-delay: 1.1s;
            animation-fill-mode: forwards;
        }
    }
`

const StyledSecond = styled.div`
    width: 100%;
    height: 33%;

    div:last-child {
        height: 100%;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        margin: 0px 0px 0px 30px;

        span:first-child {
            font-size: 40px;
            font-weight: 600;
            margin: 0px 0px 15px 0px;
            opacity: ${({located}) => located === 1 ? "0" : "1"};
        }

        span:last-child {
            font-size: 40px;
            font-weight: 600;
            margin: 0px;
            opacity: ${({located}) => located === 1 ? "0" : "1"};
        }
    }

    .active {
        span:first-child {
            transform: translateY(100px);
            animation: fadeIn 3s ease;
            animation-delay: 0.7s;
            animation-fill-mode: forwards;
        }

        span:last-child {
            transform: translateY(100px);
            animation: fadeIn 3s ease;
            animation-delay: 1.1s;
            animation-fill-mode: forwards;
        }
    }
`

const StyledThird = styled.div`
    width: 100%;
    height: 33%;

    div:last-child {
        height: 100%;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        margin: 0px 0px 0px 30px;

        span:first-child {
            font-size: 40px;
            font-weight: 600;
            margin: 0px 0px 15px 0px;
            opacity: ${({located}) => located === 2 ? "0" : "1"};
        }

        span:last-child {
            font-size: 40px;
            font-weight: 600;
            margin: 0px;
            opacity: ${({located}) => located === 2 ? "0" : "1"};
        }
    }

    .active {
        span:first-child {
            transform: translateY(100px);
            animation: fadeIn 3s ease;
            animation-delay: 0.7s;
            animation-fill-mode: forwards;
        }

        span:last-child {
            transform: translateY(100px);
            animation: fadeIn 3s ease;
            animation-delay: 1.1s;
            animation-fill-mode: forwards;
        }
    }
`

export const Content = ({located}) => {
    return (
        <StyledContent located={located}>
            <StyledFirst located={located}>
                <div>

                </div>
                <div className={located === 0 ? "active" : null}>
                    <span>사랑스러운 당신의 반려식물,</span>
                    <span>당신의 반려를 자랑해보세요!</span>
                </div>
            </StyledFirst>
            <StyledSecond located={located} className={located === 0 ? "located" : null}>
                <div>

                </div>
                <div className={located === 1 ? "active" : "none"}>
                    <span>사랑스러운 당신의 반려식물,</span>
                    <span>당신의 반려를 자랑해보세요!</span>
                </div>
            </StyledSecond>
            <StyledThird located={located}>
                <div>

                </div>
                <div className={located === 2 ? "active" : null}>
                    <span>사랑스러운 당신의 반려식물,</span>
                    <span>당신의 반려를 자랑해보세요!</span>
                </div>
            </StyledThird>
        </StyledContent>
    )
}