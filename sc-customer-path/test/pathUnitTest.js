/**
 * Empty unit tests on customer path to be completed during the dev of the features:
 */
var assert = require('assert');
describe('Path', function() {
    describe('#getCustomerPath()', function(){
        it('should return error 404 if the query contains not existing stores', function(){
            assert.equal(1 , 1);
        });
        it('should return error 400 if the customer position is empty and/or the list of store', function(){
            assert.equal(1 , 1);
        })
        it('should return error 403 if the customer is not existing in the DB', function(){
            assert.equal(1 , 1);
        });
        it('should return a path with all the store asked on the initial call', function(){
            assert.equal(1, 1);
        });
    });
});