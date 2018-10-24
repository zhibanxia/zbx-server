export default (path) => () => import(`@/views/${path}`);
