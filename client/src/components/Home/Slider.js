import a from "../../assets/img/plants/알보1.png";
import b from "../../assets/img/plants/알보2.jpg";
import c from "../../assets/img/plants/알보3.jpg";
import d from "../../assets/img/plants/알보4.jpeg";
import styled from "styled-components";
import { useState } from "react";
import { BsFillArrowLeftCircleFill } from "react-icons/bs";
import { BsFillArrowRightCircleFill } from "react-icons/bs";

const Wrapper = styled.div`
    width: 100%;

    ul {
        margin: 0px;
        padding: 0px;
        list-style: none;

        .none {
            display: none;
        }

        div {
            display: flex;
            justify-content: space-between;
            position: relative;
            top: -270px;
            height: 1px;
        }

        li div svg {
            color: rgba(999, 999, 999, 0.9);
            margin: 0px 10px 0px 10px;
            
            box-shadow: 0px 0px 5px #DBDBDB;
            border-radius: 30px;
        }

        .hidden {
            visibility: hidden;
        }
    }
`;

const Slider = () => {
    const [cur, setCur] = useState(0);
    const img = [ a, b, c, d ];

    return (
        <Wrapper>
            <ul>
                {
                    img.map((el,idx) => {
                        return (
                            <li key={idx} className={cur === idx ? null : "none"}>
                                <img src={el} alt="img" />
                                <div>
                                    <BsFillArrowLeftCircleFill onClick={() => setCur(cur - 1)} className={`pre ${cur === 0 ? "hidden" : null}`} />
                                    <BsFillArrowRightCircleFill onClick={() => setCur(cur + 1)} className={`next ${cur === img.length - 1 ? "hidden" : null}`} />
                                </div>
                            </li>
                        );
                    })
                }
            </ul>
        </Wrapper>
    )
};

export default Slider;