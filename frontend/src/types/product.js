//JSDoc 

/**
 * @typedef {Object} Product
 * @property {number} productId
 * @property {string} productName
 * @property {string} description
 * @property {number} price
 * @property {string} imageUrl
 */

// Then:
/** @type {[Product|null, Function]} */
const [product, setProduct] = useState(null);