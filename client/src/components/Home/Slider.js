import styled from "styled-components";
import { useState } from "react";
import { BsFillArrowLeftCircleFill } from "react-icons/bs";
import { BsFillArrowRightCircleFill } from "react-icons/bs";

const Wrapper = styled.div`
    ul {
        margin: 0px;
        padding: 0px;
        list-style: none;

        li {
            width: 100%;
            height: 100%;
            position: relative;
        }

        li div:first-child {
            height: ${({type}) => type ? "" : "500px"};
            background-color: black;
        }

        li div img {
            object-fit: contain;
            width: 100%;
            height: 100%;
        }

        li div:nth-of-type(2) {
            display: flex;
            justify-content: space-between;
            position: absolute;
            top: 50%;
            width: 100%;
            

            .hidden {
                visibility: hidden;
            }

            svg {
                color: rgba(999, 999, 999, 0.9);
                margin: 0px 10px 0px 10px;
                box-shadow: 0px 0px 5px #DBDBDB;
                border-radius: 30px;
            }
        }
        
        .none {
            display: none;
        }
    }
`;

const Slider = ({ imgs }) => {
    const [cur, setCur] = useState(0);

    return (
        <Wrapper>
            <ul>
                { imgs.map((img, idx) => {
                        return (
                            <li key={idx} className={cur === idx ? null : "none"}>
                                <div>
                                    <img src={img.mediaUrl} alt="img" />
                                </div>
                                { imgs.length > 1 ?
                                    <div className="arrow">
                                        <BsFillArrowLeftCircleFill onClick={() => setCur(cur - 1)} className={`pre ${cur === 0 ? "hidden" : null}`} />
                                        <BsFillArrowRightCircleFill onClick={() => setCur(cur + 1)} className={`next ${cur === imgs.length - 1 ? "hidden" : null}`} />
                                    </div> 
                                    : null
                                }
                            </li>
                        );
                    })
                }
            </ul>
        </Wrapper>
    )
};

export default Slider;