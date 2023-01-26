export default function useScroll(callback) {
    const observer = new IntersectionObserver(
        (entries, observer) => {
          entries.forEach((entry) => {
            if (entry.isIntersecting) callback();
          });
        }, { threshold: 1 }
    )
  
    const observe = (el) => {
        observer.observe(el);
    };
  
    const unObserve = (el) => {
        observer.unobserve(el);
    };
  
    return [observe, unObserve];
}