import React from 'react'
import {
  Card,
  CardContent,
  Typography,
  IconButton,
  TextField,
  Grid,
  Box
} from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import PropTypes from 'prop-types';

export default function CartItem({item, onRemove, onQuantityChange }) {
  return (
    <Card sx={{ mb: 2 }}>
      <CardContent>
        <Grid container spacing={2} alignItems="center">
          <Grid item>
            <Box
              component="img"
              src={item.imageUrl}
              alt={item.productName}
              sx={{ width: 80, height: 80, objectFit: 'cover', borderRadius: 1 }}
            />
          </Grid>

          <Grid item xs>
            <Typography variant="h6">{item.productName}</Typography>
            <Typography variant="body2">₹{item.price}</Typography>
          </Grid>

          <Grid item>
            <TextField
              type="number"
              label="Qty"
              size="small"
              value={item.quantity}
              inputProps={{ min: 1 }}
              onChange={(e) => onQuantityChange(item.productId, parseInt(e.target.value))}
              sx={{ width: 80 }}
            />
          </Grid>

          <Grid item>
            <IconButton color="error" onClick={() => onRemove(item.productId)}>
              <DeleteIcon />
            </IconButton>
          </Grid>
        </Grid>
      </CardContent>
    </Card>
  )
}

CartItem.propTypes = {
  item: PropTypes.shape({
    productId: PropTypes.number.isRequired,
    productName: PropTypes.string.isRequired,
    price: PropTypes.number.isRequired,
    imageUrl: PropTypes.string,
    quantity: PropTypes.number,
  }).isRequired,
  onRemove: PropTypes.func.isRequired,
  onQuantityChange: PropTypes.func.isRequired,
};
