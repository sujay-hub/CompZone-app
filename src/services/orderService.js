import api from './api/axios';

export const getAllOrders = () => api.get('/admin/orders'); // or '/api/admin/orders' if restricted to admin


export async function downloadInvoice(orderId) {
    try {
      const response = await fetch(`/orders/${orderId}/invoice`, {
        method: 'GET',
      });
  
      if (!response.ok) {
        throw new Error('Failed to download invoice');
      }
  
      const blob = await response.blob();
      const url = window.URL.createObjectURL(blob);
  
      const link = document.createElement('a');
      link.href = url;
      link.setAttribute('download', `invoice_${orderId}.pdf`);
      document.body.appendChild(link);
      link.click();
      link.remove();
    } catch (err) {
      console.error('Download failed:', err);
      alert('Could not download invoice.');
    }
  }