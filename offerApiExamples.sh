# Create offer
curl -XPOST -H "Content-Type: application/json" -d '
 {
   "name" : "Special Offer 1",
   "description" : "A very special offer",
   "merchant": "Merchant 1",
   "currency" : "GBP",
   "price" : 12.34
 }' localhost:2223/api/v1/offer

# Update offer
curl -XPUT -H "Content-Type: application/json" -d '
 {
   "name" : "Special Offer 1",
   "description" : "An updated special offer",
   "merchant": "Merchant 1",
   "currency" : "GBP",
   "price" : 56.78
 }' localhost:2223/api/v1/offer/1

# View offer
curl localhost:2223/api/v1/offer/1

# View all offers
curl localhost:2223/api/v1/offer

# Delete offer
curl -XDELETE localhost:2223/api/v1/offer/1
